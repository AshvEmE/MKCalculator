package com.example.lab1
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.os.Bundle
import android.widget.ListView
import android.widget.ArrayAdapter


class HistoryFragment : Fragment() {
    private lateinit var historyListView: ListView
    private var currentHistory: List<String> = emptyList()  // Зберігаємо локально історію

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_history, container, false).apply {
            historyListView = findViewById(R.id.history_list)
        }
    }

    fun updateHistory(history: List<String>) {
        currentHistory = history
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, history)
        historyListView.adapter = adapter
    }

    fun getHistory(): List<String> {
        return currentHistory
    }
}
