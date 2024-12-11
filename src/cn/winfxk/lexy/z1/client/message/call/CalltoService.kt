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
* Created Date: 2024/12/10  10:52 */
package cn.winfxk.lexy.z1.client.message.call

import cn.winfxk.lexy.z1.isRunning
import cn.winfxk.libk.log.Log
import cn.winfxk.libk.tool.Tool
import com.alibaba.fastjson2.JSONObject
import java.util.concurrent.ConcurrentHashMap

class CalltoService : Thread() {
    private val log = Log(this.javaClass.simpleName)
    private val list = ConcurrentHashMap<String, CallMessage>();

    companion object {
        private lateinit var main: CalltoService;
        fun getMain() = main;
    }

    init {
        main = this;
        log.i("呼叫监听服务启动！")
    }

    fun getList() = list;
    fun addWarning(json: JSONObject) {
        val CallID = (json["CallID"] ?: "").toString();
        if (CallID.isBlank()) {
            log.i("呼叫ID为空，忽略此报警")
            return;
        }
        var call = list[CallID];
        if (call == null) {
            log.i("正在创建一个新的报警处理器")
            call = CallMessage(CallID, json);
            list[CallID] = call;
        }
        call.resetTime();
    }

    override fun run() {
        log.i("监听服务将监听相关报警并进行显示")
        while (isRunning) {
            Tool.sleep(1000);
            if (list.isEmpty()) {
                if (CallFrame.getMain().getFrame().isVisible) CallFrame.getMain().close();
                continue;
            }
            if (! CallFrame.getMain().getFrame().isVisible) CallFrame.getMain().showFrame();
        }
    }
}