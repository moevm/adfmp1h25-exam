package com.example.examtrainer.data.local

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class StatsRepository @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    @ApplicationContext private val context: Context
) {

    //TODO
    fun getAttendance() :List<Boolean>{
        return mutableListOf();
    }

    fun getGeneralStats(){

    }

    fun getTopicsStats(){
        //context.resources
    }


}