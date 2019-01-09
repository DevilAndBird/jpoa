
package com.fh.controller.report;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.fh.common.constant_enum.DESTINSATION_TYPE;
import com.fh.common.constant_enum.INSURE_TYPE;
import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.service.report.reportFormsService;
import com.fh.util.DateUtil;
import com.fh.util.ObjectExcelView;
import com.fh.util.PageData;

/**
 * @author zhangjj
 * 报表，用于报表查询和导出
 */
@Controller
@RequestMapping(value = "reportforms")
public class reportFormsControl extends BaseController {

    @Autowired
    private reportFormsService reportFormsService;

    /**
     *
     * @Title: listReportForms
     * @Description: 汇总报表
     * @param page
     * @return
     */
    @RequestMapping(value = "listReportForms")
    public ModelAndView listReportForms(Page page) throws Exception {
        ModelAndView mv = new ModelAndView();
        PageData pd = this.getPageData();
        page.setPd(pd);
        List<PageData> reportForms = reportFormsService.reportFormslistPage(page);

        if (CollectionUtils.isNotEmpty(reportForms)) {
            mv.addObject("reportFormsList", reportForms);
        }

        mv.addObject("pd", pd);
        mv.setViewName("jpoa/reportforms/totalreport/jsp/totalreport");
        return mv;
    }
    /**
     *
     * @Title: detailReportForms
     * @Description: 明细报表
     * @param page
     * @return
     */
    @RequestMapping(value = "detailReportForms")
    public ModelAndView detailReportForms(Page page) throws Exception {
        ModelAndView mv = new ModelAndView();
        PageData pd = this.getPageData();
        page.setPd(pd);
        List<PageData> reportForms = reportFormsService.reportFormsDetaillistPage(page);

        if (CollectionUtils.isNotEmpty(reportForms)) {
            mv.addObject("reportFormsList", reportForms);
        }

        mv.addObject("pd", pd);
        mv.setViewName("jpoa/reportforms/totalreport/jsp/detailreport");
        return mv;
    }

    /**
     *
     * @Title: out汇总Excel
     * @Description: 导出excel
     * @return
     */
    @RequestMapping(value = "outExcel")
    public ModelAndView outExcel(Page page) throws Exception {
        PageData pd = this.getPageData();
        Map<String, Object> dataMap = new HashMap<String, Object>();
        List<String> titles = new ArrayList<String>();
        titles.add("序号");
        titles.add("下单日期");
        titles.add("订单状态");
        titles.add("订单来源");
        titles.add("城市");
        titles.add("订单号");
        titles.add("寄件类型");
        titles.add("QR码");
        titles.add("数量");
        titles.add("行李规格");
        titles.add("客户姓名");
        titles.add("身份证");
        titles.add("寄件人");
        titles.add("寄件联系方式");
        titles.add("寄件时间");
        titles.add("寄件地标");
        titles.add("寄件地址");
        titles.add("收件人");
        titles.add("收件联系方式");
        titles.add("收件时间");
        titles.add("收件地标");
        titles.add("收件地址");
        titles.add("基础运费");
        titles.add("投保金额");
        titles.add("保价费");
        titles.add("优惠劵");
        titles.add("格外费用");
        titles.add("应收合计");
        titles.add("是否延误");
        titles.add("退款类型");
        titles.add("退款原因");
        titles.add("退款金额");
        titles.add("退款方式");
        titles.add("退款操作人");
        titles.add("退款操作时间");
        titles.add("退款到账时间");
        titles.add("开票类型");
        titles.add("发票抬头");
        titles.add("税号");
        titles.add("发票传递方式");
        titles.add("邮箱");
        titles.add("发票收件人");
        titles.add("联系方式");
        titles.add("邮递地址");
        titles.add("下单时间");
        dataMap.put("titles", titles);

        List<PageData> varOList =  reportFormsService.reportFormslist(pd);
        List<PageData> varList = new ArrayList<PageData>();
        for (int i = 0; i < varOList.size(); i++) {
            PageData vpd = new PageData();
            String adddate = varOList.get(i).get("adddate")+"";
            String addtime = varOList.get(i).get("addtime")+"";
            Integer num = (Integer) varOList.get(i).get("num");
            Float basemoney = 0f;
            Float extramoney = 0f;
            Float cutmoney = (Float) varOList.get(i).get("cutmoney");
            String actualmoney = varOList.get(i).get("actualmoney")+"";
            String refundmoney = varOList.get(i).get("refundmoney")==null?"":varOList.get(i).get("refundmoney")+"";
            String refundtime = varOList.get(i).get("refundtime")==null?"":varOList.get(i).get("refundtime")+"";
            String taketime = varOList.get(i).get("taketime")+"";
            String sendtime = varOList.get(i).get("sendtime")+"";
            //  保费计算
            String insurecode = varOList.get(i).getString("insurecode");
            Integer premium = null;
            Integer coverage = null;
            INSURE_TYPE insureType = INSURE_TYPE.getPrem(insurecode);
            premium = insureType.getPremium() * num;
            coverage = insureType.getCoverage() * num;
           /* try {
            } catch(Exception e) {
            	Integer id = (Integer) varOList.get(i).get("id");
            	LoggerUtil.info("id:" + id + "insurecode:" + insurecode);
            	throw new RuntimeException();
            }*/

            String actionfinishtime = varOList.get(i).get("actionfinishtime")+"";
            String srcaddrtype =  varOList.get(i).getString("srcaddrtype");
            String remark = "";
            String isdelayed = "";
            if(StringUtils.isNotBlank(actionfinishtime)&&(!"null".equals(actionfinishtime))){
                if(DateUtil.compareTimeByMinute(actionfinishtime, sendtime)){
                    isdelayed="是";
                }else{
                    isdelayed="否";
                }
            }
            if(varOList.get(i).get("remark") ==null){
                remark="行李箱";
                extramoney = (float) (num * 49-49);//第一件行李免费
                basemoney = Float.valueOf(actualmoney)+ cutmoney - extramoney - premium;
            	/*try {
            	} catch(Exception e) {
            		Integer id = (Integer) varOList.get(i).get("id");
            		LoggerUtil.info("参数打印->id:" + id + "actualmoney:" + actualmoney + "cutmoney:" + cutmoney + "extramoney:" + extramoney + "premium:" + premium);
            		throw new RuntimeException();
            	}*/
            }else{
                String[] split = varOList.get(i).get("remark").toString().split("\\|");
                for (int j = 0; j < split.length; j++) {
                    String bag = split[j];
                    if(!"0".equals(bag)&&j==0&&StringUtils.isNotBlank(bag)){
                        basemoney+=69*Integer.parseInt(bag);
                        remark = "普通行李*"+bag;
                    }
                    if(!"0".equals(bag)&&j==1&&StringUtils.isNotBlank(bag)){
                        basemoney+=49*Integer.parseInt(bag);
                        remark += "小件行李*"+bag;
                    }
                    if(!"0".equals(bag)&&j==2&&StringUtils.isNotBlank(bag)){
                        basemoney+=99*Integer.parseInt(bag);
                        remark += "特殊行李*"+bag;
                    }
                }
                basemoney=basemoney+Float.valueOf(cutmoney);
                extramoney= Float.valueOf(actualmoney)+Float.valueOf(cutmoney)-basemoney- premium;
            }
            vpd.put("var1", i +"");
            vpd.put("var2", adddate);
            vpd.put("var3",varOList.get(i).get("orderstatus"));
            vpd.put("var4", varOList.get(i).get("channel"));
            vpd.put("var5", varOList.get(i).get("city"));
            vpd.put("var6", varOList.get(i).get("orderno"));
            vpd.put("var7", varOList.get(i).get("type"));
            vpd.put("var8", varOList.get(i).get("baggageid"));
            vpd.put("var9", num+"");
            vpd.put("var10",remark);
            vpd.put("var11", varOList.get(i).get("cusname"));
            vpd.put("var12", varOList.get(i).get("idno"));
            vpd.put("var13", varOList.get(i).get("sendername"));
            vpd.put("var14", varOList.get(i).get("senderphone"));
            vpd.put("var15", taketime);
            vpd.put("var16", varOList.get(i).get("scrlandmark"));
            vpd.put("var17", varOList.get(i).get("srcaddress"));
            vpd.put("var18", varOList.get(i).get("receivername"));
            vpd.put("var19", varOList.get(i).get("receiverphone"));
            vpd.put("var20", sendtime);
            vpd.put("var21", varOList.get(i).get("destlandmark"));
            vpd.put("var22", varOList.get(i).get("destaddress"));
            vpd.put("var23",basemoney+"");
            vpd.put("var24", coverage+"");
            vpd.put("var25", premium+"");
            vpd.put("var26", cutmoney+"");
            vpd.put("var27", extramoney+"");
            vpd.put("var28", actualmoney);
            vpd.put("var29", isdelayed);
            vpd.put("var30", "");
            vpd.put("var31", varOList.get(i).get("refundreason"));
            vpd.put("var32", refundmoney);
            vpd.put("var33", varOList.get(i).get("refundtype"));
            vpd.put("var34", varOList.get(i).get("operator")==null?"":varOList.get(i).get("operator").toString());
            vpd.put("var35", refundtime);
            vpd.put("var36", "");
            vpd.put("var37", varOList.get(i).get("invoicetype")==null?"":varOList.get(i).get("invoicetype").toString());
            vpd.put("var38", varOList.get(i).get("title")==null?"":varOList.get(i).get("title").toString());
            vpd.put("var39", varOList.get(i).get("taxno")==null?"":varOList.get(i).get("taxno").toString());
            vpd.put("var40", varOList.get(i).get("transtype")==null?"":varOList.get(i).get("transtype").toString());
            vpd.put("var41", varOList.get(i).get("email")==null?"":varOList.get(i).get("email").toString());
            vpd.put("var42", varOList.get(i).get("sendname"));
            vpd.put("var43", varOList.get(i).get("sendphone"));
            vpd.put("var44", varOList.get(i).get("sendaddr"));
            vpd.put("var45", addtime);
            varList.add(vpd);
        }
        dataMap.put("varList", varList);
        ObjectExcelView erv = new ObjectExcelView();
        ModelAndView mv = new ModelAndView(erv, dataMap);
        mv.addObject("pd", pd);
        return mv;
    }
    /**
     *
     * @Title: outExcel
     * @Description: 导出明细报表
     * @return
     */
    @RequestMapping(value = "outDetailExcel")
    public ModelAndView outDetailExcel(Page page) throws Exception {
        PageData pd = this.getPageData();
        Map<String, Object> dataMap = new HashMap<String, Object>();
        List<String> titles = new ArrayList<String>();
        titles.add("序号");
        titles.add("订单号");
        titles.add("执行人姓名");
        titles.add("联系方式");
        titles.add("操作岗位");
        titles.add("操作完成时间");
        titles.add("操作内容");
        titles.add("车牌号");
        titles.add("动作状态");
        titles.add("订单状态");
        dataMap.put("titles", titles);
        List<PageData> varOList =  reportFormsService.reportFormsDetaillist(pd);
        List<PageData> varList = new ArrayList<PageData>();
        for (int i = 0; i < varOList.size(); i++) {
            PageData vpd = new PageData();
            String operator_time = varOList.get(i).get("operator_time")==null?"":varOList.get(i).get("operator_time")+"";
            vpd.put("var1", i +"");
            vpd.put("var2",varOList.get(i).get("orderno"));
            vpd.put("var3", varOList.get(i).get("operator_name"));
            vpd.put("var4", varOList.get(i).get("operator_mobile"));
            vpd.put("var5", varOList.get(i).get("operator_post"));
            vpd.put("var6", operator_time);
            vpd.put("var7", varOList.get(i).get("operator_content"));
            vpd.put("var8", varOList.get(i).get("platenumber"));
            vpd.put("var9", varOList.get(i).get("isfinish"));
            vpd.put("var10", varOList.get(i).get("order_status"));
            varList.add(vpd);
        }
        dataMap.put("varList", varList);
        ObjectExcelView erv = new ObjectExcelView();
        ModelAndView mv = new ModelAndView(erv, dataMap);
        mv.addObject("pd", pd);
        return mv;
    }



}
