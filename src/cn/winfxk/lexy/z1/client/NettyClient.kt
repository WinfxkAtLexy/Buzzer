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
* Created Date: 2024/11/20  16:56 */
package cn.winfxk.lexy.z1.client

import cn.winfxk.lexy.z1.Deploy
import cn.winfxk.lexy.z1.Start
import cn.winfxk.lexy.z1.client.message.Message
import cn.winfxk.lexy.z1.client.message.OnMessageResponse
import cn.winfxk.lexy.z1.close
import cn.winfxk.lexy.z1.message.MessageType
import cn.winfxk.libk.log.Log
import cn.winfxk.libk.tool.Tool
import cn.winfxk.libk.tool.utils.toJson
import io.netty.bootstrap.Bootstrap
import io.netty.channel.Channel
import io.netty.channel.ChannelInitializer
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.nio.NioSocketChannel
import io.netty.handler.codec.string.StringDecoder
import io.netty.handler.codec.string.StringEncoder
import io.netty.util.CharsetUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import java.util.concurrent.ConcurrentHashMap

class NettyClient(private val start: Start) : ChannelInitializer<Channel>(), AutoCloseable {
    @Volatile
    private var reconnectCount = 0;
    private var group = NioEventLoopGroup();
    private val log by lazy { Log(this.javaClass.simpleName) }
    lateinit var channel: Channel;
    private val request = ConcurrentHashMap<String, OnMessageResponse>();
    @Volatile
    private var isRunning = false;
    @Volatile
    private var isConnected = false;
    fun isConnected() = isConnected;
    fun isRunning(): Boolean = isRunning;
    fun sendMessage(message: Message, response: OnMessageResponse? = null) {
        val msg = message.toString();
        if (message.getType().equals(MessageType.Request) && response != null) {
            log.i("正在发送消息并等待回执[${message.getID()}][${msg.length}]..")
            addResponse(message.getID(), response);
        } else if (message.getType() == MessageType.Response)
            Log.i("正在回复消息[${message.getID()}][${msg.length}]..")
        else if (message.getType() != MessageType.Live) Log.i("正在发送消息[${message.getID()}][${msg.length}]..")
        channel.writeAndFlush(msg)
    }

    init {
        log.i("NettyClient加载中...")
        main = this;
    }

    companion object {
        private lateinit var main: NettyClient;
        fun getMain(): NettyClient = main;
        val scope = CoroutineScope(Dispatchers.Default);
    }

    fun getRequest() = request;
    private fun addResponse(ID: String, response: OnMessageResponse) {
        request[ID] = response;
    }

    fun getGroup() = group;
    fun start() {
        log.i("正在启动客户端")
        isRunning = true;
        val bootstrap = Bootstrap();
        group = NioEventLoopGroup();
        bootstrap.group(group).channel(NioSocketChannel::class.java).handler(this);
        try {
            log.i("正在连接服务器")
            val connectFuture = bootstrap.connect(Deploy.deploy.host, Deploy.deploy.port).sync();
            channel = connectFuture.channel();
            log.i("已连接至服务器[${Deploy.deploy.host}:${Deploy.deploy.port}]！")
            Thread {
                Tool.sleep(1000);
                log.i("正在发送客户端上线请求");
                sendMessage(Message(true, Deploy.deploy.Title, """
                    {
                      "Name": "${Deploy.deploy.Name}",
                      "ID": "${Deploy.deploy.ID}",
                      "Time": "${Tool.getDate()} ${Tool.getTime()}"
                    }
                """.trimIndent().toJson(), MessageType.Request)) {
                    log.i("客户端上线!")
                    reconnectCount = 0;
                    isConnected = true;
                }
            }.start()
        } catch (e: Exception) {
            log.w("请求链接失败！！正在尝试重连..")
            Tool.sleep(1000)
            if (start.isVisible) start.title = "请求链接失败！！正在尝试重连.."
            group.shutdownGracefully();
            reconnect()
        }
    }

    override fun initChannel(ch: Channel) {
        log.i("创建初始化链接并添加管道")
        val pipeline = ch.pipeline();
        pipeline.addLast(StringDecoder(CharsetUtil.UTF_8));
        pipeline.addLast(StringEncoder(CharsetUtil.UTF_8));
        pipeline.addLast(ClientHandler());
    }

    fun shutdown() {
        isRunning = false;
        log.i("正在关闭链接")
        group.shutdownGracefully();
    }
    @Synchronized
    fun reconnect() {
        isConnected = false;
        val exit = 10;
        Tool.sleep(1000)
        log.i("正在第${reconnectCount}/${exit}次尝试重连")
        Tool.sleep(400)
        if (! isRunning) {
            log.e("链接已被关闭！停止重连。")
            if (start.isVisible) start.title = "链接已被关闭！停止重连。"
            return;
        }
        if (reconnectCount ++ > 10) {
            log.e("重连次数已达上限！程序即将在${exit}秒后退出！")
            if (start.isVisible) start.title = "重连次数已达上限！程序即将在${exit}秒后退出！"
            Thread {
                for (i in 0 .. exit) {
                    Tool.sleep(1000);
                    log.e("无法连接至服务器！程序将在${exit-i}秒后退出！")
                    if (start.isVisible) start.title = "无法连接至服务器！程序将在${exit-i}秒后退出！"
                }
                close(- 1)
            }.start()
            return;
        }
        var time = Tool.getRand(5, 15);
        for (i in 0 .. time) {
            log.i("${time - i}秒后重连..")
            if (start.isVisible) start.title = "${time - i}秒后重连.."
            Tool.sleep(1000)
        }
        log.i("重连中...")
        if (start.isVisible) start.title = "重连中..."
        try {
            start()
            log.i("重连成功！")
            if (start.isVisible) start.title = "重连成功！"
        } catch (e: Exception) {
            log.e("重连失败！", e)
            if (start.isVisible) start.title = "重连失败！${e.message}"
            time = Tool.getRand(5, 15);
            for (i in 0 .. time) {
                log.i("${time - i}秒后重连..")
                if (start.isVisible) start.title = "${time - i}秒后重连.."
                Tool.sleep(1000)
            }
            reconnect()
        }
    }

    override fun close() {
        shutdown()
    }
}