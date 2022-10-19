package com.altyyev.todo.todo.ui.fragments.update

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.ALARM_SERVICE
import android.content.Intent
import android.icu.util.Calendar
import android.media.RingtoneManager
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.altyyev.todo.R
import com.altyyev.todo.databinding.FragmentUpdateBinding
import com.altyyev.todo.todo.data.Priority
import com.altyyev.todo.todo.data.RoomModel
import com.altyyev.todo.todo.receiver.AlarmReceiver
import com.altyyev.todo.todo.ui.fragments.add.AddNewViewModel
import com.altyyev.todo.todo.util.Constants.Companion.NOTIFICATION_CHANNEL_NAME
import com.altyyev.todo.todo.util.Constants.Companion.NOTIFICATION_ID
import com.altyyev.todo.todo.util.viewBinding
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.MaterialTimePicker.INPUT_MODE_CLOCK
import com.google.android.material.timepicker.TimeFormat
import dagger.hilt.android.AndroidEntryPoint
import java.util.Calendar.*

@AndroidEntryPoint
class UpdateFragment : Fragment(R.layout.fragment_update) {
    private val navArgs by navArgs<UpdateFragmentArgs>()
    private val binding by viewBinding(FragmentUpdateBinding::bind)
    private val viewModel: UpdateViewModel by viewModels()
    private val addViewModel: AddNewViewModel by viewModels()
    private lateinit var alarmManager: AlarmManager
    private lateinit var pendingIntent: PendingIntent

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        createNotificationChannel()
        updateTodo()
        binding.run {
            messageText.setText(navArgs.item.message)
            titleText.setText(navArgs.item.title)
            spinner.setSelection(parsePriority(navArgs.item.priority))
            spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    when (position) {
                        0 -> {
                            (parent!!.getChildAt(0) as TextView).setTextColor(
                                ContextCompat.getColor(
                                    requireContext(),
                                    R.color.red
                                )
                            )
                        }
                        1 -> {
                            (parent!!.getChildAt(0) as TextView).setTextColor(
                                ContextCompat.getColor(
                                    requireContext(),
                                    R.color.green
                                )
                            )
                        }
                        2 -> {
                            (parent!!.getChildAt(0) as TextView).setTextColor(
                                ContextCompat.getColor(
                                    requireContext(),
                                    R.color.orange
                                )
                            )
                        }
                    }
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                }
            }
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel =
                NotificationChannel(NOTIFICATION_ID, NOTIFICATION_CHANNEL_NAME, importance)
            channel.description = getString(R.string.you_added_reminder)
            val notificationManager =
                getSystemService(requireContext(), NotificationManager::class.java)
            notificationManager?.createNotificationChannel(channel)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.update_fragment_menu, menu)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.delete -> {
                viewModel.deleteTodo(navArgs.item)
                findNavController().navigate(R.id.action_updateFragment2_to_mainFragment)
                Toast.makeText(requireContext(), getString(R.string.Deleted), Toast.LENGTH_SHORT).show()
            }
            R.id.time -> {
                val picker =
                    MaterialTimePicker.Builder()
                        .setTimeFormat(TimeFormat.CLOCK_24H)
                        .setHour(12)
                        .setMinute(0)
                        .setTitleText(getString(R.string.selecet_time))
                        .setInputMode(INPUT_MODE_CLOCK)
                        .build()
                picker.show(requireActivity().supportFragmentManager, "123")
                picker.addOnPositiveButtonClickListener {
                    val calendar = getInstance()
                    calendar[Calendar.HOUR_OF_DAY] = picker.hour
                    calendar[MINUTE] = picker.minute
                    calendar[MILLISECOND] = 0
                    calendar[SECOND] = 0
                    alarmManager = requireActivity().getSystemService(ALARM_SERVICE) as AlarmManager
                    val intent = Intent(requireActivity(), AlarmReceiver::class.java)
                    pendingIntent = PendingIntent.getBroadcast(requireContext(), 0, intent, 0)
                    alarmManager.setExact(
                        AlarmManager.RTC_WAKEUP,
                        calendar.timeInMillis,
                        pendingIntent
                    )
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.reminder_successfully_added),
                        Toast.LENGTH_SHORT
                    ).show()
                }

            }
        }
        return super.onOptionsItemSelected(item)
    }


    private fun updateTodo() = with(binding) {
        updateButton.setOnClickListener {
            val validation = viewModel.checkUserValidation(
                title = titleText.text.toString(),
                message = messageText.text.toString()
            )
            if (validation) {
                viewModel.updateTodo(
                    RoomModel(
                        title = binding.titleText.text.toString(),
                        message = binding.messageText.text.toString(),
                        priority = addViewModel.parsePriority(spinner.selectedItem.toString()),
                        id = navArgs.item.id
                    )
                )
                findNavController().navigate(R.id.action_updateFragment2_to_mainFragment)
            } else {
                Snackbar.make(requireView(), getString(R.string.validation_is_not_successfully), Snackbar.LENGTH_LONG)
                    .show()
            }

        }
    }

    private fun parsePriority(priority: Priority): Int {
        return when (priority) {
            Priority.HIGH -> 0
            Priority.MEDIUM -> 2
            Priority.LOW -> 1
        }
    }
}
