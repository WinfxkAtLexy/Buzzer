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
* Created Date: 2024/11/21  14:12 */
package cn.winfxk.lexy.z1.client

import cn.winfxk.lexy.z1.client.message.Message
import cn.winfxk.lexy.z1.client.message.ReceiveMessages
import cn.winfxk.lexy.z1.message.MessageType
import cn.winfxk.libk.log.Log
import cn.winfxk.libk.tool.utils.objToString
import cn.winfxk.libk.tool.utils.toJson
import com.alibaba.fastjson2.JSONObject
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.ChannelInboundHandlerAdapter
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ClientHandler : ChannelInboundHandlerAdapter() {
    private val log by lazy { Log(this.javaClass.simpleName) }
    @Volatile
    private var isRunning: Boolean = true

    init {
        NettyClient.scope.launch {
            while (isRunning) {
                delay(10000)
                if (! NettyClient.getMain().isConnected()) continue
                NettyClient.getMain().sendMessage(Message(true, type = MessageType.Live, message = ""))
            }
        }
    }

    override fun channelRead(ctx: ChannelHandlerContext, msg: Any?) {
        val message = msg?.toString()
        if (msg == null || message.isNullOrBlank()) {
            log.i("接收到空消息，忽略")
            return;
        }
        try {
            val root = message.toJson();
            val type = root["type"].objToString();
            val messageID = root["ID"].objToString();
            if (type.isNullOrBlank() || messageID.isNullOrBlank()) {
                log.e("请求异常！跳过请求操作.$root");
                return;
            }
            val json = root["json"];
            if (json == null || json !is JSONObject) {
                log.e("请求异常！未知的请求体。跳过请求操作.$root")
                return;
            }
            if (type == MessageType.Response.name) {
                log.i("正在执行回执[$messageID].");
                val response = NettyClient.getMain().getRequest().remove(messageID);
                if (response == null) {
                    log.i("回执处理器[$messageID]未找到。")
                    return;
                }
                response.onResponse(json);
                return;
            }
            if (! type.equals(MessageType.Request.name)) {
                log.i("跳过一个非正常请求[$type]")
                return;
            }
            log.i("等待消息处理[$messageID]")
            ReceiveMessages(messageID, json).onMessage()?.also {
                it.setID(messageID)
                it.setType(MessageType.Response)
                NettyClient.getMain().sendMessage(it);
            }
            log.i("消息处理完毕[$messageID].")
        } catch (e: Exception) {
            log.e("处理消息时发生异常！", e)
        }
    }
    /**
     * 链接活跃
     */
    override fun channelActive(ctx: ChannelHandlerContext?) {
        isRunning = true;
        super.channelActive(ctx)
    }
    /**
     * 当链接不在活跃
     */
    override fun channelInactive(ctx: ChannelHandlerContext?) {
        isRunning = false;
        super.channelInactive(ctx)
        NettyClient.getMain().getGroup().shutdownGracefully();
        if (cn.winfxk.lexy.z1.isRunning) {
            log.w("链接失效！正在尝试重连..")
            NettyClient.getMain().reconnect()
        }else log.i("关闭与服务器的链接")
    }
}