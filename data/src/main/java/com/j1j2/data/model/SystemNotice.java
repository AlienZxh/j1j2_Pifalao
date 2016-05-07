package com.j1j2.data.model;

/**
 * Created by alienzxh on 16-5-2.
 */
public class SystemNotice {

    /**
     * NoticeId : 1
     * NotifyText : <p style="TEXT-ALIGN: center"><strong><span style="FONT-SIZE: 16px; FONT-FAMILY: 宋体"></span></strong></p><p style="TEXT-ALIGN: center"><strong style="TEXT-ALIGN: left; LINE-HEIGHT: 1.5; TEXT-INDENT: 44pt"><span style="FONT-FAMILY: 宋体">温馨提示</span></strong> </p><p style="TEXT-ALIGN: center"><strong style="TEXT-ALIGN: left; LINE-HEIGHT: 1.5; TEXT-INDENT: 44pt"><span style="FONT-FAMILY: 宋体"><span style="FONT-SIZE: 14px; LINE-HEIGHT: 1.5"><p>&nbsp;<strong style="TEXT-ALIGN: left; LINE-HEIGHT: 1.5; TEXT-INDENT: 44pt"><span style="FONT-SIZE: 14px; FONT-FAMILY: 宋体; LINE-HEIGHT: 1.5">批发佬仓库于2016年4月30日至2016年5月1日进行放假（原因：五一仓库放假，劳动人民都值得尊敬），期间订单将于2016年5月2日统一发货。预祝大家五一快乐！</span></strong></p></span><span style="FONT-SIZE: 14px; LINE-HEIGHT: 1.5"></span></span></strong><p></p><p>&nbsp;</p><p style="TEXT-ALIGN: center"><br/></p><p style="TEXT-ALIGN: center"><strong style="TEXT-ALIGN: left; LINE-HEIGHT: 1.5; TEXT-INDENT: 44pt"><span style="FONT-FAMILY: 宋体"><span style="FONT-SIZE: 14px; LINE-HEIGHT: 1.5"><span style="WHITE-SPACE: nowrap"></span></span></span></strong></p><p style="TEXT-ALIGN: center"><strong style="TEXT-ALIGN: left; LINE-HEIGHT: 1.5; TEXT-INDENT: 44pt"><span style="FONT-FAMILY: 宋体"><span style="FONT-SIZE: 14px"><span style="FONT-SIZE: 14px; FONT-FAMILY: 宋体, Arial, Tahoma; WHITE-SPACE: normal; COLOR: #444444; BACKGROUND-COLOR: #ffffff"></span><strong style="WHITE-SPACE: normal; LINE-HEIGHT: 1.5; TEXT-INDENT: 44pt"><span style="FONT-FAMILY: 宋体"><span style="FONT-SIZE: 14px"></span></span></strong></span></span></strong></p><p style="TEXT-ALIGN: center"><span style="FONT-FAMILY: 宋体; TEXT-ALIGN: right; LINE-HEIGHT: 1.5; TEXT-INDENT: 250.8pt"><span style="FONT-SIZE: 14px"><strong>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; 2016</strong></span></span><span style="FONT-FAMILY: 宋体; TEXT-ALIGN: right; LINE-HEIGHT: 200%; TEXT-INDENT: 250.8pt"><span style="FONT-SIZE: 14px; LINE-HEIGHT: 1.5"><strong>年4</strong></span><span style="FONT-SIZE: 14px; LINE-HEIGHT: 1.5"><strong>月27</strong></span><span style="FONT-SIZE: 14px; LINE-HEIGHT: 1.5"><strong>日&nbsp;</strong></span></span> </p><p><br/></p><p style="TEXT-ALIGN: right"><span style="FONT-SIZE: 14px"></span><span style="FONT-SIZE: 18pt"></span></p></p>
     * State : false
     */

    private int NoticeId;
    private String NotifyText;
    private boolean State;

    public int getNoticeId() {
        return NoticeId;
    }

    public void setNoticeId(int NoticeId) {
        this.NoticeId = NoticeId;
    }

    public String getNotifyText() {
        return NotifyText;
    }

    public void setNotifyText(String NotifyText) {
        this.NotifyText = NotifyText;
    }

    public boolean isState() {
        return State;
    }

    public void setState(boolean State) {
        this.State = State;
    }
}
