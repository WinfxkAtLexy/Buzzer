/* 
* Copyright Notice
* © [2024] Winfxk. All rights reserved.
* The software, its source code, and all related documentation are the intellectual property of Winfxk. Any reproduction or distribution of this software or any part thereof must be clearly attributed to Winfxk and the original author. Unauthorized copying, reproduction, or distribution without proper attribution is strictly prohibited.
* For inquiries, support, or to request permission for use, please contact us at:
* Email: admin@winfxk.cn
* QQ: 2508543202
* Visit our homepage for more information: http://Winfxk.cn
* 
* --------- Create message ---------
* Created by IntelliJ ID
* Author： Winfxk
* Created PCUser: kc4064 
* Web: http://winfxk.com
* Created Date: 2024/11/22  09:23 */
package cn.winfxk.lexy.z1

import cn.winfxk.lexy.z1.log.Logsave
import cn.winfxk.libk.config.Config
import cn.winfxk.libk.log.Log
import cn.winfxk.libk.tool.Tool
import cn.winfxk.libk.tool.utils.getImageByjar
import cn.winfxk.libk.tool.utils.getStreamByJar
import cn.winfxk.libk.tool.utils.readFont
import cn.winfxk.libk.tool.utils.throwException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import java.awt.Dimension
import java.awt.Toolkit
import java.awt.image.BufferedImage
import java.io.File
import java.util.*

class Deploy {
    val toolkit: Toolkit = Toolkit.getDefaultToolkit();
    val screenSize: Dimension = toolkit.screenSize;
    val defaultDir = Logsave.defaultDir;
    val config = Config(File(defaultDir, "config.json"))
    private val log = Log(this.javaClass.simpleName)
    var host: String = config.getString("host", "") ?: "";
    var port = config.getInt("port", 1998);
    var Title = config.getString("Title").let {
        if (it.isNullOrBlank()) {
            val title = "客户端：${Tool.CompressNumber(System.currentTimeMillis())}"
            config.set("Title", title).save();
            return@let title;
        }
        it;
    };
    var ID = config.getString("ID").let {
        if (it.isNullOrBlank()) {
            val ID = UUID.randomUUID().toString() + "-${Tool.CompressNumber(System.currentTimeMillis())}";
            config.set("ID", ID).save();
            return@let ID;
        }
        it;
    }
    var Name = config.getString("Name").let {
        if (it.isNullOrBlank()) {
            val name = Title;
            config.set("Title", name).save();
            return@let name;
        }
        it;
    };
    var ClientType = config.getString("ClientType").let {
        if (it.isNullOrBlank()) {
            val name = "All";
            config.set("ClientType", name).save();
            return@let name;
        }
        it;
    }

    //图标
    private lateinit var icon: BufferedImage;
    private lateinit var SettingIcon: BufferedImage;
    private lateinit var close: BufferedImage;
    private lateinit var call: BufferedImage;


    companion object {
        val deploy = Deploy();
        val scope = CoroutineScope(Dispatchers.Default);
        val fonts by lazy { MyFont(); }
        @Volatile
        private var isInitialized = false;
    }

    fun init() {
        if (isInitialized) return;
        isInitialized = true;
        log.i("正在初始化应用程序配置..")
        icon = this.getImageByjar("Icon.png") ?: throwException("无法加载资源：Icon.png");
        close = this.getImageByjar("close.png") ?: throwException("无法加载资源：close.png");
        SettingIcon = this.getImageByjar("SettingIcon.png") ?: throwException("无法加载资源：SettingIcon.png");
        call = this.getImageByjar("Call.png") ?: throwException("无法加载资源：Call.png");
        Thread {
            log.i("配置文件守护服务启动.")
            while (isRunning) {
                Tool.sleep(600000)
                log.i("正在保存配置文件")
                config.save();
            }
            config.save();
        }.start()
    }

    fun getCloseIcon() = close;
    fun getSettingIcon() = SettingIcon;
    fun getIcon() = icon;
    fun getCallIcon() = call;


    class MyFont {
        private val log by lazy { Log(this.javaClass.simpleName) }
        val hwct = this.getStreamByJar("hwct.ttf".also { log.i("加载字体资源：$it") })?.readFont() ?: throwException("hwct.ttf");
    }

    fun isValidAddress(address: String?): Boolean {
        try {
            if (address.isNullOrBlank()) return false;
            if (! address.contains(".")) return false;
            if (address.startsWith(".") || address.endsWith(".")) return false;
            val parts = address.split(".")
            if (parts.size < 2) return false;
            return parts.all { it.isNotBlank() && it.matches("""^[a-zA-Z0-9-]{1,63}$""".toRegex()) }
        } catch (_: Exception) {
            return false;
        }
    }
}