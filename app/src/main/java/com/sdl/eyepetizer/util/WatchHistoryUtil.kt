package com.sdl.eyepetizer.util

import android.content.Context
import android.util.Base64
import java.io.*

class WatchHistoryUtil {

    companion object {
        /**
         * 保存在手机里面的文件名
         */
        private val FILE_NAME = "eyepetizer_watch_history"

        /**
         * 保存数据
         */
        fun put(context: Context,key: String,value: Any) {
            val sp = context.getSharedPreferences(FILE_NAME,Context.MODE_PRIVATE)
            val editor = sp.edit()
            when (value) {
                is String -> editor.putString(key, value)
                is Int -> editor.putInt(key, value)
                is Boolean -> editor.putBoolean(key, value)
                is Long -> editor.putLong(key, value)
                is Float -> editor.putFloat(key, value)
                else -> editor.putString(key,value.toString())
            }
            editor.apply()
        }

        fun get(context: Context,key: String,defaultValue: Any): Any? {
            val sp = context.getSharedPreferences(FILE_NAME,Context.MODE_PRIVATE)
            return when (defaultValue) {
                is String -> sp.getString(key,defaultValue)
                is Int -> sp.getInt(key,defaultValue)
                is Boolean -> sp.getBoolean(key,defaultValue)
                is Long -> sp.getLong(key,defaultValue)
                is Float -> sp.getFloat(key,defaultValue)
                else -> null
            }
        }

        /**
         * 移除某个key
         */
        fun remove(context: Context,key: String) {
            val sp = context.getSharedPreferences(FILE_NAME,Context.MODE_PRIVATE)
            sp.edit().remove(key).apply()
        }

        /**
         * 清楚所有数据
         */
        fun clear(context: Context) {
            context.getSharedPreferences(FILE_NAME,Context.MODE_PRIVATE).edit().clear().apply()
        }

        /**
         * 保存数据
         */
        fun put(fileName: String,context: Context,key: String,value: Any) {
            val sp = context.getSharedPreferences(fileName,Context.MODE_PRIVATE)
            val editor = sp.edit()
            when (value) {
                is String -> editor.putString(key, value)
                is Int -> editor.putInt(key, value)
                is Boolean -> editor.putBoolean(key, value)
                is Long -> editor.putLong(key, value)
                is Float -> editor.putFloat(key, value)
                else -> editor.putString(key,value.toString())
            }
            editor.apply()
        }

        fun get(fileName: String,context: Context,key: String,defaultValue: Any): Any? {
            val sp = context.getSharedPreferences(fileName,Context.MODE_PRIVATE)
            return when (defaultValue) {
                is String -> sp.getString(key,defaultValue)
                is Int -> sp.getInt(key,defaultValue)
                is Boolean -> sp.getBoolean(key,defaultValue)
                is Long -> sp.getLong(key,defaultValue)
                is Float -> sp.getFloat(key,defaultValue)
                else -> null
            }
        }

        /**
         * 移除某个key
         */
        fun remove(fileName: String,context: Context,key: String) {
            val sp = context.getSharedPreferences(fileName,Context.MODE_PRIVATE)
            sp.edit().remove(key).apply()
        }

        /**
         * 清楚所有数据
         */
        fun clear(fileName: String,context: Context) {
            context.getSharedPreferences(fileName,Context.MODE_PRIVATE).edit().clear().apply()
        }

        fun putObject(fileName: String,context: Context,key: String,obj: Any?) {
            val sp = context.getSharedPreferences(fileName,Context.MODE_PRIVATE)
            if (obj == null) {
                sp.edit().remove(key).apply()
                return
            }
            val baos = ByteArrayOutputStream()
            var oos: ObjectOutputStream? = null
            try {
                oos = ObjectOutputStream(baos)
                oos.writeObject(obj)
            } catch (e: IOException) {
                e.printStackTrace()
            }
            //将对象转换成byte数组，并将其进行base64编码
            val objectStr = String(Base64.encode(baos.toByteArray(),Base64.DEFAULT))
            try {
                baos.close()
                oos?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            sp.edit().putString(key,objectStr).apply()
        }

        fun getObject(fileName: String,context: Context,key: String): Any? {
            val sp = context.getSharedPreferences(fileName,Context.MODE_PRIVATE)
            val wordBase64 = sp.getString(key,"")
            return readStrToObject(wordBase64)
        }

        fun readStrToObject(value: String): Any? {
            try {
                if (value.isNullOrEmpty()) {
                    return null
                }
                val objBytes = Base64.decode(value.toByteArray(),Base64.DEFAULT)
                val bis = ByteArrayInputStream(objBytes)
                val ois = ObjectInputStream(bis)
                //将byte数组转换成product对象
                val obj = ois.readObject()
                ois.close()
                bis.close()
                return obj
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return null
        }

        /**
         * 返回所有的键值对
         */
        fun getAll(fileName: String,context: Context): Map<String,*> {
            return context.getSharedPreferences(fileName,Context.MODE_PRIVATE).all
        }
    }

}