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
                            ## UNIX-подобные операционные системы
                            
                            **Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed aliquam tempor eros eget scelerisque. Etiam condimentum non nunc non porta. Aliquam eu risus neque. Phasellus pretium sit amet ipsum at aliquet. Cras vitae velit ut erat tincidunt suscipit ac ac nibh. Quisque vehicula mi eros, vel blandit elit imperdiet a. Donec ut lorem vitae lorem feugiat porta sit amet vel magna. Proin maximus ex nec nisl vehicula rutrum.**
                            
                            *Lorem ipsum dolor sit amet*:
                            - Curabitur convallis;
                            - Tellus quis convallis blandit;
                            - Praesent lorem nunc.
                            
                            Praesent lorem nunc, sagittis non elit et, vestibulum ullamcorper erat. Mauris lorem orci, dapibus ut feugiat vel, mattis nec ipsum. Vivamus sit amet massa scelerisque, venenatis magna at, malesuada nisl. Donec nec volutpat felis. Donec non risus enim. Donec a magna nisl. Ut imperdiet auctor bibendum. Suspendisse pulvinar quis enim non laoreet. Mauris id consectetur dui, sed egestas sapien. Morbi viverra, urna et lobortis volutpat, tellus sem lobortis risus, non fermentum massa nunc pretium nibh. Etiam dapibus odio venenatis tempor gravida.
                            
                            Pellentesque pulvinar diam quis ante rhoncus dignissim. Nam vel magna aliquam, dignissim erat sit amet, cursus quam. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Ut malesuada sodales ante, consequat viverra erat. Vestibulum at lacus vehicula, commodo nulla in, convallis sem. Sed id fermentum risus. Sed finibus in lacus vitae viverra. Aenean nec iaculis lacus. Duis pellentesque tincidunt ligula non eleifend. Nunc quis malesuada est, id elementum dui. Nunc faucibus tincidunt ligula. Integer arcu erat, bibendum a turpis eget, semper efficitur ipsum. Aliquam non elementum dolor. Cras hendrerit augue eu aliquet efficitur. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas.

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
            Chapter(
                title = "Очень-очень-очень-очень-очень-очень-очень-очень длинное-длинное название главы",
                sections = emptyList(),
            ),
            Chapter(
                title = "Очень-очень-очень-очень-очень-очень-очень-очень длинное-длинное название главы",
                sections = emptyList(),
            ),
            Chapter(
                title = "Очень-очень-очень-очень-очень-очень-очень-очень длинное-длинное название главы",
                sections = emptyList(),
            ),
            Chapter(
                title = "Очень-очень-очень-очень-очень-очень-очень-очень длинное-длинное название главы",
                sections = emptyList(),
            ),
            Chapter(
                title = "Очень-очень-очень-очень-очень-очень-очень-очень длинное-длинное название главы",
                sections = emptyList(),
            ),
            Chapter(
                title = "Очень-очень-очень-очень-очень-очень-очень-очень длинное-длинное название главы",
                sections = emptyList(),
            ),
            Chapter(
                title = "Очень-очень-очень-очень-очень-очень-очень-очень длинное-длинное название главы",
                sections = emptyList(),
            ),
            Chapter(
                title = "Очень-очень-очень-очень-очень-очень-очень-очень длинное-длинное название главы",
                sections = emptyList(),
            ),
            Chapter(
                title = "Очень-очень-очень-очень-очень-очень-очень-очень длинное-длинное название главы",
                sections = emptyList(),
            ),
            Chapter(
                title = "Очень-очень-очень-очень-очень-очень-очень-очень длинное-длинное название главы",
                sections = emptyList(),
            ),
            Chapter(
                title = "Очень-очень-очень-очень-очень-очень-очень-очень длинное-длинное название главы",
                sections = emptyList(),
            ),
            Chapter(
                title = "Очень-очень-очень-очень-очень-очень-очень-очень длинное-длинное название главы",
                sections = emptyList(),
            ),
            Chapter(
                title = "Очень-очень-очень-очень-очень-очень-очень-очень длинное-длинное название главы",
                sections = emptyList(),
            ),
        )
    }
}