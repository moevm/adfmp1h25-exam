package com.example.examtrainer.data.local

import com.example.examtrainer.domain.model.Chapter
import com.example.examtrainer.domain.model.ChapterQuestions
import com.example.examtrainer.domain.model.Question
import com.example.examtrainer.domain.model.Section

class TheoryRepository {
    private var _chapters: List<Chapter> = emptyList()

    fun getChapters(): List<Chapter> {
        return _chapters
    }

    fun getChaptersQuestions(): List<ChapterQuestions> {
        return _chapters.map { c ->
            ChapterQuestions(
                title = c.title,
                questions = c.questions,
            )
        }
    }

    init {
        _chapters = listOf(
            Chapter(
                title = "Знакомство с ОС «Альт»",
                sections = listOf(
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
                questions = listOf(
                    Question(
                        "Что такое Kotlin?",
                        listOf("Язык", "Фреймворк", "БД", "ОС"),
                        "Язык",
                        "подсказка: Язык"
                    ),
                    Question(
                        "Android основан на?",
                        listOf("Windows", "Linux", "macOS", "DOS"),
                        "Linux",
                        "подсказка: Linyx"
                    ),
                ),
            ),
            Chapter(
                title = "Основы работы в командной строке",
                sections = listOf(
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
                questions = listOf(
                    Question(
                        "Что такое Kotlin? #2",
                        listOf("Язык", "Фреймворк", "БД", "ОС"),
                        "Язык",
                        "подсказка: Язык"
                    ),
                    Question(
                        "Android основан на? #2",
                        listOf("Windows", "Linux", "macOS", "DOS"),
                        "Linux",
                        "подсказка: Linyx"
                    ),
                ),
            ),
        )

        for (i in 1..15) {
            _chapters = _chapters + Chapter(
                title = "Очень-очень-очень-очень-очень-очень-очень-очень длинное-длинное название главы",
                sections = emptyList(),
                questions = emptyList(),
            )
        }
    }
}