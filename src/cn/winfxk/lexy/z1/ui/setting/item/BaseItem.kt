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
* Created Date: 2024/12/11  15:24 */
package cn.winfxk.lexy.z1.ui.setting.item

import cn.winfxk.lexy.z1.Deploy
import cn.winfxk.libk.config.Config
import cn.winfxk.tool.view.MyJPanel
import java.awt.Color
import java.awt.Font

abstract class BaseItem : MyJPanel() {
    companion object {
        val labelFont = Font("楷体", Font.BOLD, 18);
        val labelColor = Color.black;
        val deploy = Deploy.deploy;
        val jg = 4;
        val lvalueFont = Font("楷体", Font.BOLD, 18);
        val lvalueColor = Color.blue;
    }

    abstract fun save(config: Config);
    abstract fun legal(): Boolean;
}