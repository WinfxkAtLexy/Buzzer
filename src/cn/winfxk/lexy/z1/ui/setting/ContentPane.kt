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
* Created Date: 2024/12/11  15:25 */
package cn.winfxk.lexy.z1.ui.setting

import cn.winfxk.lexy.z1.Deploy
import cn.winfxk.lexy.z1.client.NettyClient
import cn.winfxk.lexy.z1.ui.setting.item.*
import cn.winfxk.libk.tool.Tool
import cn.winfxk.tool.view.MyJPanel
import cn.winfxk.tool.view.dialog.Toast

class ContentPane(private val main: SettingUI) : MyJPanel() {
    private val list = ArrayList<BaseItem>();
    private val itemHeight = 40;
    private val itemJG = 3;
    val log = main.log;

    init {
        addItem(NameSet());
        addItem(TitleSet());
        addItem(ServerSet(this))
        addItem(TypeSet(this))
    }

    override fun start() {
        for (index in 0 ..< list.size) {
            list[index].also {
                it.setSize(width - BaseItem.labelFont.size, itemHeight)
                it.setLocation(BaseItem.labelFont.size / 2, index * itemHeight + index * itemJG);
                it.start();
            }
        }
    }

    private fun addItem(item: BaseItem) {
        list.add(item)
        add(item);
    }

    fun save() {
        Deploy.deploy.config.also { config ->
            if (! list.all { it.legal() }) return;
            list.forEach { it.save(config) }
            if (config.save()) Toast("保存成功！")
            else Toast("保存失败！")
            main.log.i("用户保存客户端设置")
            Thread {
                Tool.sleep(1000);
                NettyClient.getMain().sendOnlineMessage();
            }.start()
        }
    }
}