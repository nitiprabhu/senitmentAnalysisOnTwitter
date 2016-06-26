package com.ise.project.sentiAnalysis;

import com.googlecode.charts4j.PieChart;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by India on 20-Apr-16.
 */
@Controller
public class AlgorithmController {

    private static Logger logger = org.slf4j.LoggerFactory.getLogger(AlgorithmController.class);



    @RequestMapping(value = "offline/{id}")
    public String offlinePage(@PathVariable("id") String id, Model model) throws IOException {
        List<Integer> result1 = new ArrayList<>();
        List<Integer> result2 = new ArrayList<>();
        DatumBoxClass datumBoxClass = new DatumBoxClass();
        ProcessClass processClass = new ProcessClass();
        logger.info("Entered into Offline/{}",id);

        result1 = Application.processor.getOfflineDatumResult(id);

//        result1.add(0,1);
//        result1.add(1,1);
//        result1.add(2,1);
        logger.warn("Result1 {}", result1);

        logger.warn("Result after clear {}", result1);
        logger.info("Completed DatumBox");

        result2 = processClass.getOurAlgoResult(id);
        logger.warn("After algo {}", result2);
        logger.info("Result2 {}",result2);

//        model.addAttribute("id",id);
        model.addAttribute("result2",result2);
        model.addAttribute("result1",result1);
        PieChart pieChart = CreatePieChart.getPieChart("DatumBox",result1);
        PieChart pieChart1 = CreatePieChart.getPieChart("Algorithm",result2);
//        System.out.println(pieChart.toURLString());
        model.addAttribute("url1",pieChart.toURLString());
        model.addAttribute("url2",pieChart1.toURLString());
        return "offlineFile";
    }

    @RequestMapping(value = "/offline")
    public String getOffline(Model model)
    {
        logger.info("Entered into offline page");
        model.addAttribute("link","file:///C:/Users/India/Desktop/twitter-analysis-mastespringboot/core/src/main/resources/images/2.jpg");
        return "offlineIndex";
    }


    @RequestMapping(value = "/result1")
    public String checkResult1(Model model) {
        PieChart pieChart;
        logger.warn("Entered into Result1 page");
//        Processor.datumResultList.forEach(System.out::println);
        model.addAttribute("result1Lists", Processor.datumResultList);
        model.addAttribute("result2Lists", Processor.algoResultList);
        pieChart = CreatePieChart.getPieChart("DatumBox",Processor.datumResultList);
        logger.info("PieChart URL1 {}",pieChart.toURLString());
        model.addAttribute("url1", pieChart.toURLString());
        pieChart = CreatePieChart.getPieChart("AlgoResult",Processor.algoResultList);
        logger.info("PieChart URL2 {}",pieChart.toURLString());
        model.addAttribute("url2",pieChart.toURLString());
        return "secondFile";
    }

    @RequestMapping(value = "/")
    public String getMethod(Model model) {
        logger.info("Entered into getMethod state");
        model.addAttribute("link","C:\\Users\\India\\Desktop\\2.jpg");
        return "update";
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String postMethod(@ModelAttribute("trend") String trend, @ModelAttribute("quant") Integer quant, Model model, RedirectAttributes redirectAttributes) throws Exception {
        try {
            Application.processor.analyzeTrends(trend, quant);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        System.out.println("Completed scanning");
        model.addAttribute("list", Processor.controllerList);
        redirectAttributes.addAttribute("list", Processor.controllerList);
        System.out.println("Completed Printing");
        return "firstFile";
    }
}