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
* Created Date: 2024/12/11  15:38 */
package cn.winfxk.lexy.z1.ui.setting.item

import cn.winfxk.lexy.z1.ui.setting.item.show.ShowGUI
import cn.winfxk.lexy.z1.ui.setting.item.show.ShowMini
import cn.winfxk.libk.config.Config

class ShowSet : BaseItem() {
    private val mini = ShowMini();
    private val gui = ShowGUI();

    init {
        mini.setLocation(0, 0);
        add(mini);
        add(gui);
    }

    override fun save(config: Config) {
        mini.save(config);
        gui.save(config);
    }

    override fun legal() = mini.legal() && gui.legal();

    override fun start() {
        val width = this.width / 2;
        mini.setSize(width, this.height);
        mini.start();
        gui.setSize(width, this.height);
        gui.setLocation(width, 0)
        gui.start();
    }
}