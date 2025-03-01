package com.example.examtrainer.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.examtrainer.domain.model.Chapter
import com.example.examtrainer.domain.model.Section
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class TheoryViewModel : ViewModel() {
    private val _chapters = MutableStateFlow<List<Chapter>>(emptyList())
    val chapters: StateFlow<List<Chapter>> = _chapters

    private val _currentChapterIdx = MutableStateFlow(0)
    val currentChapterIdx: StateFlow<Int> = _currentChapterIdx

    private val _currentSectionIdx = MutableStateFlow(0)
    val currentSectionIdx: StateFlow<Int> = _currentSectionIdx

    init {
        loadData()
    }

    fun selectChapter(chapterIdx: Int) {
        _currentChapterIdx.value = chapterIdx
    }

    fun selectSection(sectionIdx: Int) {
        _currentSectionIdx.value = sectionIdx
    }

    private fun loadData() {
        _chapters.value = listOf(
            Chapter(
                "Знакомство с ОС «Альт»",
                listOf(
                    Section(
                        "UNIX-подобные операционные системы",
                        """
                            Какой-то контент
                            
                            Контент продолжается...
                        """.trimIndent()
                    ),
                    Section(
                        "Проект GNU",
                        """
                            Какой-то контент
                            
                            Контент продолжается...
                        """.trimIndent()
                    ),
                    Section(
                        "ОС на основе ядра Linux",
                        """
                            Какой-то контент
                            
                            Контент продолжается...
                        """.trimIndent()
                    ),
                    Section(
                        "История ОС «Альт»",
                        """
                            Какой-то контент
                            
                            Контент продолжается...
                        """.trimIndent()
                    ),
                    Section(
                        "Репозиторий Sisyphus (Сизиф)",
                        """
                            Какой-то контент
                            
                            Контент продолжается...
                        """.trimIndent()
                    ),
                    Section(
                        "Продукция «Базальт СПО»",
                        """
                            Какой-то контент
                            
                            Контент продолжается...
                        """.trimIndent()
                    ),
                ),
            ),
            Chapter(
                "Основы работы в командной строке",
                listOf(
                    Section(
                        "Интерфейс командной строки",
                        """
                            Какой-то контент
                            
                            Контент продолжается...
                        """.trimIndent()
                    ),
                    Section(
                        "Типы команд",
                        """
                            Какой-то контент
                            
                            Контент продолжается...
                        """.trimIndent()
                    ),
                    Section(
                        "Терминалы",
                        """
                            Какой-то контент
                            
                            Контент продолжается...
                        """.trimIndent()
                    ),
                    Section(
                        "Навигация по дереву каталогов",
                        """
                            Какой-то контент
                            
                            Контент продолжается...
                        """.trimIndent()
                    ),
                    Section(
                        "История команд",
                        """
                            Какой-то контент
                            
                            Контент продолжается...
                        """.trimIndent()
                    ),
                    Section(
                        "Перенаправление консольного ввода-вывода",
                        """
                            Какой-то контент
                            
                            Контент продолжается...
                        """.trimIndent()
                    ),
                    Section(
                        "Специальные символы командного интерпретатора",
                        """
                            Какой-то контент
                            
                            Контент продолжается...
                        """.trimIndent()
                    ),
                    Section(
                        "Объединение выполнения команд",
                        """
                            Какой-то контент
                            
                            Контент продолжается...
                        """.trimIndent()
                    ),
                ),
            ),
        )
    }
}