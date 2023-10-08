package com.gxx.datalibrary.inter

interface OnSharePreferencesMigrationDataFinish {

    fun onSuccess()

    fun onFail(e:Throwable)
}