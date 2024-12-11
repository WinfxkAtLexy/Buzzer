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
* Created Date: 2024/12/10  11:04 */
package cn.winfxk.lexy.z1.client.message.call

import cn.winfxk.libk.log.Log
import cn.winfxk.libk.tool.Tool
import com.alibaba.fastjson2.JSONObject

class CallMessage(val key: String, val json: JSONObject) : Runnable, AutoCloseable {
    private var time = System.currentTimeMillis();
    private val log = Log(this.javaClass.simpleName)
    @Volatile
    private var isRuning = true;

    init {
        log.i("正在初始化报警管理器")
        Thread(this).start();
        log.i("报警管理器已启动")
    }

    override fun run() {
        var new: Long;
        while (isRunning()) {
            new = time;
            Tool.sleep(4000);
            if (! isRunning() || new == time) {
                remove();
                break;
            }
        }
    }

    private fun remove() {
        log.i("报警请求已失效！正在删除")
        isRuning = false;
        CalltoService.getMain().getList().remove(key);
        close();
    }

    fun isRunning() = cn.winfxk.lexy.z1.isRunning && isRuning;
    override fun close() {
        log.i("正在关闭报警管理器.")
        isRuning = false;
        log.i("报警管理器已关闭.")
    }

    fun resetTime() {
        time = System.currentTimeMillis();
    }
}