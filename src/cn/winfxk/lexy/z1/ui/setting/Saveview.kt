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
* Created Date: 2024/12/11  15:39 */
package cn.winfxk.lexy.z1.ui.setting

import cn.winfxk.tool.view.MyJPanel
import cn.winfxk.tool.view.button.Button

class Saveview(private val main: SettingUI) : MyJPanel() {
    private val save: Button = Button("保存");

    init {
        save.setOnClickListener { main.contentPane.save(); }
        add(save);
    }

    override fun start() {
        save.setSize(100, height - 4)
        save.setLocation(width / 2 - save.width / 2, height / 2 - save.height / 2);
        save.start();
    }
}