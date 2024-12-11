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
* Created Date: 2024/12/11  13:50 */
package cn.winfxk.lexy.z1.client.message.rec

import cn.winfxk.lexy.z1.Deploy
import cn.winfxk.lexy.z1.client.message.Message
import cn.winfxk.lexy.z1.client.message.ReceiveMessages
import cn.winfxk.lexy.z1.client.message.call.CalltoService
import cn.winfxk.libk.log.Log
import com.alibaba.fastjson2.JSONObject

class CallMessage(main: ReceiveMessages) : Handling(main.messageID, main.json) {
    private val key = (json["CallID"] ?: "").toString();
    private val log = Log(this.javaClass.simpleName)

    companion object {
        const val type = "CallTo";
        private val emptyJson = JSONObject();
    }

    override fun onRec(): Message {
        if (key.isBlank()) {
            log.i("未知的呼叫Key！跳过处理");
            return Message(isSuccess = false, message = "未知的呼叫Key！跳过处理", json = emptyJson);
        }
        val ClientID = (json["ClientID"] ?: "").toString();
        if (ClientID?.equals(Deploy.deploy.ID) == true) return Message(isSuccess = true, message = "跳过本人呼叫")
        CalltoService.getMain().addWarning(json);
        return Message(isSuccess = true, "呼叫成功！", json = emptyJson);
    }
}