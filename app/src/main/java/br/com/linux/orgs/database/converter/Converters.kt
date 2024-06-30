package br.com.linux.orgs.database.converter

import androidx.room.TypeConverter
import java.math.BigDecimal

class Converters {
    @TypeConverter
    fun bigDecimalToDouble(value: BigDecimal?): Double? {
        return value?.toDouble()
    }

    @TypeConverter
    fun doubleToBigDecimal(value: Double?): BigDecimal {
        return value?.let { BigDecimal(it.toString()) } ?: BigDecimal.ZERO
    }
}