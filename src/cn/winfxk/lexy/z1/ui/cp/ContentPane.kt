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
* Created Date: 2024/12/5  13:20 */
package cn.winfxk.lexy.z1.ui.cp

import cn.winfxk.lexy.z1.ui.cp.call.ie.IE
import cn.winfxk.lexy.z1.ui.cp.call.iqc.IQC
import cn.winfxk.lexy.z1.ui.cp.call.technology.Technology
import cn.winfxk.lexy.z1.ui.cp.call.warehouse.Warehouse
import cn.winfxk.tool.view.MyJPanel

class ContentPane : MyJPanel() {
    private val technology = Technology();
    private val ie = IE();
    private val warehouse = Warehouse();
    private val iqc = IQC();

    init {
        add(technology);
        add(ie);
        add(warehouse);
        add(iqc);
    }

    override fun start() {
    }
}