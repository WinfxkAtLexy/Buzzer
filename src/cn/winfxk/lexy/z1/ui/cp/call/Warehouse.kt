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
* Created Date: 2024/12/5  17:18 */
package cn.winfxk.lexy.z1.ui.cp.call

import java.awt.Color

/**
 * 仓库
 */
class Warehouse : Section() {
    companion object {
        private val color = Color(255, 50, 255);
    }

    override fun getColor() = color;
    override fun getString() = "呼叫仓库"
    override fun getStringColor(): Color = Color.white;
}