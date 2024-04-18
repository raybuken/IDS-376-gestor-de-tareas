package com.example.tasktify

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.tasktify.adapter.TaskListAdapter
import com.example.tasktify.model.Task

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class TasktifyInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.example.tasktify", appContext.packageName)
    }

    @Test
    fun validateTaskItems() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        val taskList = getTaskList()

        val taskListAdapter = TaskListAdapter(appContext, taskList)

        val firstTask = taskListAdapter.getItem(0)
        val secondTask = taskListAdapter.getItem(1)

        assertEquals(taskList[0].id, firstTask.id)
        assertEquals(taskList[1].id, secondTask.id)
    }

    @Test
    fun taskListSizeIsCorrect() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        val taskList = getTaskList()

        val taskListAdapter = TaskListAdapter(appContext, taskList)

        assertEquals(4,taskListAdapter.count)
    }

    private fun getTaskList(): List<Task>{
        val task1 = Task(
            id = "1",
            title = "Task 1",
            date = "02/10/2024",
            description = "Lorem ipsum dolor"
        )

        val task2 = Task(
            id = "2",
            title = "Task 2",
            date = "06/10/2024",
            description = "Lorem ipsum dolor"
        )

        val task3 = Task(
            id = "3",
            title = "Task 3",
            date = "02/01/2024",
            description = "Lorem ipsum dolor"
        )

        val task4 = Task(
            id = "4",
            title = "Task 4",
            date = "02/05/2024",
            description = "Lorem ipsum dolor"
        )

        val taskList = mutableListOf<Task>()
        taskList.add(task1)
        taskList.add(task2)
        taskList.add(task3)
        taskList.add(task4)

        return taskList
    }
}