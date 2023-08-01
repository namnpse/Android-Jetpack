package com.namnp.androidjetpack.flow

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.namnp.androidjetpack.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch


class InstantSearchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_instant_search)

        val textViewResult = findViewById<TextView>(R.id.search_result)
        val searchView = findViewById<SearchView>(R.id.search_view)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                searchView.getQueryTextChangeStateFlow()
                    .debounce(300)
                    .filter { query ->
                        if (query.isEmpty()) {
                            textViewResult.text = ""
                            return@filter false
                        } else {
                            return@filter true
                        }
                    }
                    .distinctUntilChanged()
                    .flatMapLatest { query ->
                        dataFromNetwork(query)
                            .catch {
                                emitAll(flowOf(""))
                            }
                    }
                    .flowOn(Dispatchers.IO)
                    .collect { result ->
                        textViewResult.text = result
                    }
            }
        }
    }
}

fun SearchView.getQueryTextChangeStateFlow(): StateFlow<String> {

    val query = MutableStateFlow("")

    setOnQueryTextListener(object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?): Boolean {
            return true
        }

        override fun onQueryTextChange(newText: String): Boolean {
            query.value = newText
            return true
        }
    })

    return query
}

/**
 * Simulation of network data
 */
private fun dataFromNetwork(query: String): Flow<String> {
    return flow {
        delay(2000)
        emit(query)
    }
}