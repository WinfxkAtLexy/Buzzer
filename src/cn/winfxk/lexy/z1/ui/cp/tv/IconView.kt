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
* Created Date: 2024/12/5  14:29 */
package cn.winfxk.lexy.z1.ui.cp.tv

import cn.winfxk.lexy.z1.Deploy
import cn.winfxk.lexy.z1.ui.GUI
import cn.winfxk.lexy.z1.ui.setting.SettingUI
import cn.winfxk.tool.view.MyJPanel
import cn.winfxk.tool.view.image.ImageView

class IconView(private val main: GUI) : MyJPanel() {
    private val close = ImageView(Deploy.deploy.getCloseIcon());
    private val setting = ImageView(Deploy.deploy.getSettingIcon());

    init {
        close.setOnClick {
            main.windowClosing(null)
            main.frame.isVisible = false;
        }
        add(close);
        setting.setOnClick { SettingUI.getMain().showFrame() }
        add(setting)
    }

    override fun start() {
        close.setSize(height, height)
        close.setLocation(width - close.width, 0);
        close.start();
        setting.size = close.size;
        setting.setLocation(close.x - setting.width - 1, 0);
        setting.start();
    }
}