package com.ruoyi.base.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author shiva   2022/3/13 20:46
 */
public class OldIpMap {
    public static Map<String, String> IP_MAP = new HashMap<String, String>() {
        {
            /*****************************PPF2A01*****************************/
            //SAP
            put("192.168.80.31PPF2A01", "192.168.80.151");
            put("192.168.80.32PPF2A01", "192.168.80.151");
            put("192.168.80.33PPF2A01", "192.168.80.151");
            put("192.168.80.34PPF2A01", "192.168.80.151");
            put("192.168.80.35PPF2A01", "192.168.80.152");
            put("192.168.80.36PPF2A01", "192.168.80.152");

/*****************************PPC2A01*****************************/
//AE
            put("192.168.70.31PPC2A01", "192.168.70.151");
            put("192.168.70.32PPC2A01", "192.168.70.151");
            put("192.168.70.33PPC2A01", "192.168.70.151");
            put("192.168.70.34PPC2A01", "192.168.70.151");

            put("192.168.70.35PPC2A01", "192.168.70.152");
            put("192.168.70.36PPC2A01", "192.168.70.152");

            put("192.168.70.37PPC2A01", "192.168.70.152");
            put("192.168.70.38PPC2A01", "192.168.70.152");


/*****************************PPC1A01*****************************/
            put("192.168.50.31PPC1A01", "192.168.50.151");
            put("192.168.50.32PPC1A01", "192.168.50.151");
            put("192.168.50.33PPC1A01", "192.168.50.151");
            put("192.168.50.34PPC1A01", "192.168.50.151");

            put("192.168.50.35PPC1A01", "192.168.50.152");
            put("192.168.50.36PPC1A01", "192.168.50.152");

            put("192.168.50.37PPC1A01", "192.168.50.153");
            put("192.168.50.38PPC1A01", "192.168.50.153");

 /****************************EVA厂PPC8601**********************************************/
            put("192.168.90.31PPC8601","192.168.90.151");
            put("192.168.90.32PPC8601","192.168.90.151");
            put("192.168.90.33PPC8601","192.168.90.151");
            put("192.168.90.34PPC8601","192.168.90.151");

            put("192.168.90.35PPC8601","192.168.90.152");
            put("192.168.90.36PPC8601","192.168.90.152");

            put("192.168.90.37PPC8601","192.168.90.153");
            put("192.168.90.38PPC8601","192.168.90.153");

            //三井
            put("192.168.110.31PPZ1101","192.168.110.151");
            put("192.168.110.32PPZ1101","192.168.110.151");
            put("192.168.110.33PPZ1101","192.168.110.151");
            put("192.168.110.34PPZ1101","192.168.110.151");

            put("192.168.110.35PPZ1101","192.168.110.152");
            put("192.168.110.36PPZ1101","192.168.110.152");

            //港务
            put("192.168.100.31PMA1001","192.168.100.151");
            put("192.168.100.32PMA1001","192.168.100.151");
            put("192.168.100.33PMA1001","192.168.100.151");
            put("192.168.100.34PMA1001","192.168.100.151");
            put("192.168.100.35PMA1001","192.168.100.152");
            put("192.168.100.36PMA1001","192.168.100.152");

            put("192.168.100.37PMA1001","192.168.100.154");
            put("192.168.100.38PMA1001","192.168.100.154");


            //pp
//            put("192.168.3.1PPCP101","192.168.60.151");
//            put("192.168.3.2PPCP101","192.168.60.151");
//            put("192.168.3.3PPCP101","192.168.60.152");
//            put("192.168.3.4PPCP101","192.168.60.152");
//            put("192.168.3.8PPCP101","192.168.60.155");

            put("192.168.60.35PPCP101","192.168.60.151");
            put("192.168.60.36PPCP101","192.168.60.151");
            put("192.168.60.39PPCP101","192.168.60.152");
            put("192.168.60.40PPCP101","192.168.60.152");
            put("192.168.60.37PPCP101","192.168.60.155");
            put("192.168.60.38PPCP101","192.168.60.155");
            put("192.168.60.41PPCP101","192.168.60.153");
            put("192.168.60.42PPCP101","192.168.60.153");


            //三井
            put("192.168.110.31PPZ1101","192.168.110.151");
            put("192.168.110.32PPZ1101","192.168.110.151");
            put("192.168.110.33PPZ1101","192.168.110.151");
            put("192.168.110.34PPZ1101","192.168.110.151");

            put("192.168.110.35PPZ1101","192.168.110.152");
            put("192.168.110.36PPZ1101","192.168.110.152");

            //港务
            put("192.168.3.21PMA1001","192.168.100.151");
            put("192.168.3.22PMA1001","192.168.100.151");
            put("192.168.3.1PMA1001","192.168.100.151");
            put("192.168.3.2PMA1001","192.168.100.151");
            put("192.168.3.23PMA1001","192.168.100.152");
            put("192.168.3.24PMA1001","192.168.100.152");

        }
    };

    public static Map<String, String> REWRITE_IP_MAP = new HashMap<String, String>() {
        {
            /***********************************/
            put("192.168.70.31", "192.168.70.31");
            put("192.168.70.32", "192.168.70.32");
            put("192.168.70.33", "192.168.70.33");
            put("192.168.70.34", "192.168.70.34");

            put("192.168.70.61", "192.168.70.33");
            put("192.168.70.62", "192.168.70.34");

            put("192.168.70.35", "192.168.70.35");
            put("192.168.70.36", "192.168.70.36");
            put("192.168.70.37", "192.168.70.37");
            put("192.168.70.38", "192.168.70.38");

            put("192.168.70.63", "192.168.70.37");
            put("192.168.70.64", "192.168.70.38");

            /***********************************/
            put("192.168.80.31", "192.168.80.31");
            put("192.168.80.32", "192.168.80.32");
            put("192.168.80.33", "192.168.80.33");
            put("192.168.80.34", "192.168.80.34");

            put("192.168.80.61", "192.168.80.33");
            put("192.168.80.62", "192.168.80.34");
            put("192.168.80.35", "192.168.80.35");
            put("192.168.80.36", "192.168.80.36");

            /*******************************************/
            put("192.168.50.31", "192.168.50.31");
            put("192.168.50.32", "192.168.50.32");
            put("192.168.50.33", "192.168.50.33");
            put("192.168.50.34", "192.168.50.34");
            put("192.168.50.35", "192.168.50.35");
            put("192.168.50.36", "192.168.50.36");

            put("192.168.50.37", "192.168.50.37");
            put("192.168.50.38", "192.168.50.38");
            put("192.168.50.61", "192.168.50.37");
            put("192.168.50.62", "192.168.50.38");

            /********************************************/
            put("192.168.90.31","192.168.90.31");
            put("192.168.90.32","192.168.90.32");
            put("192.168.90.33","192.168.90.33");
            put("192.168.90.34","192.168.90.34");
            put("192.168.90.61","192.168.90.33");
            put("192.168.90.62","192.168.90.34");

            put("192.168.90.35","192.168.90.35");
            put("192.168.90.36","192.168.90.36");
            put("192.168.90.37","192.168.90.37");
            put("192.168.90.38","192.168.90.38");
            put("192.168.90.63","192.168.90.37");
            put("192.168.90.64","192.168.90.38");

            put("192.168.110.31","192.168.110.31");
            put("192.168.110.32","192.168.110.32");
            put("192.168.110.33","192.168.110.33");
            put("192.168.110.34","192.168.110.34");
            put("192.168.110.35","192.168.110.35");
            put("192.168.110.36","192.168.110.36");
            put("192.168.110.61","192.168.110.33");
            put("192.168.110.62","192.168.110.34");

            //港务
            put("192.168.100.31","192.168.100.31");
            put("192.168.100.32","192.168.100.32");
            put("192.168.100.33","192.168.100.33");
            put("192.168.100.34","192.168.100.34");
            put("192.168.100.35","192.168.100.35");
            put("192.168.100.36","192.168.100.36");
            put("192.168.100.61","192.168.100.33");
            put("192.168.100.62","192.168.100.34");
            put("192.168.100.37","192.168.100.37");
            put("192.168.100.38","192.168.100.38");

            //pp
//            put("192.168.60.35","192.168.3.1");
//            put("192.168.60.36","192.168.3.2");
//            put("192.168.60.39","192.168.3.3");
//            put("192.168.60.40","192.168.3.4");
//            put("192.168.60.61","192.168.3.3");
//            put("192.168.60.62","192.168.3.4");
//            put("192.168.60.37","192.168.3.7");
            put("192.168.60.35","192.168.60.35");
            put("192.168.60.36","192.168.60.36");
            put("192.168.60.39","192.168.60.39");
            put("192.168.60.40","192.168.60.40");
            put("192.168.60.61","192.168.60.61");
            put("192.168.60.62","192.168.60.62");
            put("192.168.60.37","192.168.60.37");
            put("192.168.60.38","192.168.60.38");
            put("192.168.60.41","192.168.60.41");
            put("192.168.60.42","192.168.60.42");
            put("192.168.60.63","192.168.60.63");
            put("192.168.60.64","192.168.60.64");



            put("192.168.110.31","192.168.110.31");
            put("192.168.110.32","192.168.110.32");
            put("192.168.110.33","192.168.110.33");
            put("192.168.110.34","192.168.110.34");
            put("192.168.110.35","192.168.110.35");
            put("192.168.110.36","192.168.110.36");
            put("192.168.110.61","192.168.110.33");
            put("192.168.110.62","192.168.110.34");

            //港务
            put("192.168.100.31","192.168.3.21");
            put("192.168.100.32","192.168.3.32");
            put("192.168.100.33","192.168.3.1");
            put("192.168.100.34","192.168.3.2");
            put("192.168.100.35","192.168.3.23");
            put("192.168.100.36","192.168.3.24");

        }
    };
}
