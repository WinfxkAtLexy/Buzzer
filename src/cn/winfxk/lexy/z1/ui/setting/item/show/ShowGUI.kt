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
* Created Date: 2024/12/12  15:40 */
package cn.winfxk.lexy.z1.ui.setting.item.show

import cn.winfxk.lexy.z1.ui.GUI
import cn.winfxk.lexy.z1.ui.setting.item.BaseItem
import cn.winfxk.libk.config.Config
import javax.swing.JCheckBox
import javax.swing.SwingConstants

class ShowGUI : BaseItem() {
    private val label = JCheckBox("显示用户报警界面", deploy.config.getBoolean("显示用户界面", true));

    init {
        label.setLocation(0, 0);
        label.verticalAlignment = SwingConstants.CENTER;
        label.background = GUI.backg;
        label.font = labelFont;
        label.isOpaque = false;
        label.foreground = labelColor;
        add(label);
    }

    override fun start() {
        label.size = size;
    }

    override fun save(config: Config) {
        config.set("显示用户界面", label.isSelected)
    }

    override fun legal() = true;
}