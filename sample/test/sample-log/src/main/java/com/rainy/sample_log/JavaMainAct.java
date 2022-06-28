package com.rainy.sample_log;

import android.widget.Button;

import com.rainy.log.Logger;
import com.rainy.test.ui.SampleAct;

import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;

/**
 * @author : jianghaiyang
 * @Description:
 * @date: 2022/6/28 01:05
 * @version: 1.0.0
 */
public class JavaMainAct extends SampleAct {
    @Override
    public void onCreate() {
        addClick("init", () -> {
            init();
            return null;
        });
    }

    private void init() {
        Logger.INSTANCE.create(getApplication())
                // 是否打印控制台日志
                // .setConsoleLogOpen(true)
                // 设置日志级别
                // .setLogLevel(LogLevel.DEBUG)
                // 设置文件前缀名称
                // .setPeFixName("Logger")
                // 设置文件最大数量
                // .setMaxFileSize(2)
                // 单个文件的大小
                // .setSingleFileLength(2)
                // 添加自定义的适配器
                // .addLogAdapter()
                // 设置实际保存log位置
                // .setLogPath()
                .init()
    }

}
