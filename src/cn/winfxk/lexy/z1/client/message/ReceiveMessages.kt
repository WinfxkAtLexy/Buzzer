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
* Created Date: 2024/11/22  08:34 */
package cn.winfxk.lexy.z1.client.message

import cn.winfxk.lexy.z1.client.message.rec.CallMessage
import cn.winfxk.lexy.z1.client.message.rec.EmptyMessage
import cn.winfxk.libk.log.Log
import com.alibaba.fastjson2.JSONObject

/**
 * 接收到消息的处理类
 */
class ReceiveMessages(val messageID: String, val json: JSONObject) {
    private val log = Log(this.javaClass.simpleName)

    companion object {
        private val emptyJson = JSONObject();
    }

    init {
        log.i("客户端消息处理器启动！");
    }

    fun onMessage(): Message? {
        val type = (json["type"] ?: "").toString();
        if (type.isBlank()) {
            log.i("接收到空类型消息！跳过处理");
            return Message(isSuccess = false, json = emptyJson, message = "接收到空类型消息！跳过处理")
        }
        return when (type.lowercase()) {
            CallMessage.type.lowercase() -> CallMessage(this)
            else                         -> EmptyMessage(this)
        }.onRec()
    }
}