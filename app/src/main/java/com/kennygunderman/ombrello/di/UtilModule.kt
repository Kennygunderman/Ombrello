package com.kennygunderman.ombrello.di

import com.kennygunderman.ombrello.util.DateUtil
import com.kennygunderman.ombrello.util.IDateUtil
import org.koin.dsl.module

val utilModule = module {
    factory<IDateUtil> { DateUtil() }
}