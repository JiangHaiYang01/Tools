package com.rainy.log.handler

import android.util.Log
import com.rainy.log.mode.AppLog
import java.io.File
import java.io.FileWriter
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

/**
 *
 * @Author  Allens
 * @Date:   2021/6/28 9:53 下午
 * @Version 1.0
 * @Desc
 */
class WriterHandler {

    companion object {

        private const val DEBUG = false

        private const val TAG = "WriterHandler"

        private const val SEPARATOR = ","

        private const val NEW_LINE_REPLACEMENT = " <br> "
    }

    private val mDateFormat = SimpleDateFormat("yyyy-MM-dd-HH:mm:ss.SSS", Locale.getDefault())

    private val mDateFormatWithLogTime =
        SimpleDateFormat("yyyy.MM.dd HH:mm:ss.SSS", Locale.getDefault())

    private val mDate = Date()

    private val mLine = System.getProperty("line.separator")

    private val mFileComparator = FileComparator()

    /**
     * single Thread pool
     */
    private val mExecutor: ExecutorService = Executors.newSingleThreadExecutor()

    private var mFileWriter: FileWriter? = null

    private lateinit var mCurrentLogPath: String

    fun print(appLog: AppLog) {
        mExecutor.execute {
            synchronized(WriterHandler) {
                writeLog(appLog)
            }
        }
    }

    private fun writeLog(appLog: AppLog) {
        try {
            // 创建文件夹
            mkdir()
            if (mFileWriter == null) {
                mCurrentLogPath = createNewLogFile()
                if (DEBUG) {
                    Log.i(TAG, "mCurrentLogPath is $mCurrentLogPath")
                }
                mFileWriter = FileWriter(mCurrentLogPath, true)
            }
            if (isFull(File(mCurrentLogPath))) {
                Log.i(TAG, "log write is full")
                mFileWriter = null
                writeLog(appLog)
                return
            }
            mFileWriter?.write(appendLog(appLog))
            mFileWriter?.flush()
        } catch (t: Throwable) {
            Log.e(TAG, "write failed:${t.message}")
            mFileWriter?.flush()
            mFileWriter?.close()
        }
    }

    /**
     * 日志格式
     */
    private fun appendLog(appLog: AppLog): String {

        var logInfo = appLog.log

        mDate.time = System.currentTimeMillis()
        val builder = StringBuilder()
        // date
        builder.append(mDateFormatWithLogTime.format(mDate))
        builder.append(SEPARATOR)

        // level
        builder.append(appLog.logLevel.info)
        builder.append(SEPARATOR)

        // tag
        builder.append(appLog.tag)
        builder.append(SEPARATOR)

        // message
        if (mLine != null) {
            if (logInfo.contains(mLine)) {
                logInfo = logInfo.replace(mLine.toRegex(), NEW_LINE_REPLACEMENT)
            }
        }
        builder.append(logInfo)

        // new line
        builder.append(mLine)
        return builder.toString()
    }

    /**
     * 创建日志文件夹
     */
    private fun mkdir() {
        val logPath = LoggerHandler.getConfig().logPath
        val file = File(logPath)
        if (!file.exists()) {
            file.mkdirs()
        }
    }

    /**
     * 获取日志文件位置
     */
    private fun createNewLogFile(): String {
        val logPath = LoggerHandler.getConfig().logPath
        // 如果目录是空的 创建一个新的日志文件
        val file = File(logPath)
        val listFiles = file.listFiles()
        if (listFiles == null || listFiles.isEmpty()) {
            return formatLogName(logPath)
        }
        // 将文件重新排序找出最新创建的文件
        val toList = listFiles.toList()
        Collections.sort(toList, mFileComparator)
        // 当文件都相同的时候 取最后一个
        if (isFull(listFiles[toList.size - 1])) {
            return deleteFile(logPath, listFiles, toList)
        }
        // 如果没有写满，继续写入此文件
        return listFiles[0].path
    }

    private fun deleteFile(
        logPath: String,
        listFiles: Array<File>,
        toList: List<File>
    ): String {
        // 创建新日志
        val path = formatLogName(logPath)
        if (DEBUG) {
            Log.w(TAG, "create new file log name:$path")
        }
        if (listFiles.size > LoggerHandler.getConfig().maxFileSize) {
            // 删除最早创建的日志
            Collections.sort(toList, mFileComparator)
            listFiles[0].delete()
            if (DEBUG) {
                Log.w(TAG, "delete log file name:${listFiles[0].name}")
            }
        }
        // 重新创建新的对象
        mFileWriter = null
        return path
    }

    private fun formatLogName(logPath: String): String {
        val date: String = mDateFormat.format(Date())
        val preFixName = LoggerHandler.getConfig().preFixName
        return if (preFixName.isEmpty()) {
            String.format("%s%s%s.log", logPath + File.separator, date)
        } else {
            String.format("%s%s-%s.log", logPath + File.separator, preFixName, date)
        }
    }

    /**
     * 文件是否超过最大文件长度
     */
    private fun isFull(file: File): Boolean {
        if (!file.exists()) {
            if (DEBUG) {
                Log.e(TAG, "check file full [${file.name}] failed : not exists")
            }
            return false
        }
        if (file.isDirectory) {
            if (DEBUG) {
                Log.e(TAG, "check file full [${file.name}] failed : is directory")
            }
            return false
        }
        return (file.length() > LoggerHandler.getConfig().maxFileLength)
    }

    class FileComparator : Comparator<File> {
        override fun compare(o1: File, o2: File): Int {
            return o1.lastModified().compareTo(o2.lastModified())
        }
    }

}