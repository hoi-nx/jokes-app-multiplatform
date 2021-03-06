package com.kurt.jokes.mobile.data.local

import com.kurt.jokes.JokeDb
import com.kurt.jokes.JokesDatabase
import com.kurt.jokes.mobile.domain.entities.Joke
import com.squareup.sqldelight.db.SqlDriver

class JokesLocalSource {
    var driver: SqlDriver? = JokesDatabaseDriver.jokesDatabaseDriver

    private val db by lazy {
        val driver = requireNotNull(driver) { "No SqlDriver found!" }
        JokesDatabase.invoke(driver).jokeQueries
    }

    fun getJokes(): List<Joke> {
        return db.getAllJokes().executeAsList().map {
            Joke(
                id = it.id,
                setup = it.setup,
                punchline = it.punchline,
                type = it.type
            )
        }
    }

    fun saveJokes(jokes: List<Joke>) {
        jokes.forEach {
            db.insertJoke(
                JokeDb.Impl(
                    id = it.id,
                    setup = it.setup,
                    punchline = it.punchline,
                    type = it.type
                )
            )
        }
    }
}