package com.lxw.mytool.entity;

        import com.lxw.mytool.util.ExcelUtil;
        import org.junit.Test;

        import java.io.File;
        import java.util.ArrayList;
        import java.util.List;

public class MatAndCostTest {
    File file = new File("C:\\Users\\lixiewen\\Desktop\\物料编码与成本中心\\物料与成本中心对应关系整理-钢轧.xlsx");
//    int sheetIndex = 7;有问题
//    int sheetIndex = 8;
    int sheetIndex = 5;
    @Test
    public void test1() throws Exception {
        List<MatAndCost> matAndCosts = new ArrayList<>();
        List<List<String>> rows = ExcelUtil.excel2List(file, sheetIndex, true, true);
        for (int ri = 0; ri < rows.size(); ri++) {
            List<String> cells = rows.get(ri);
            for (int ci = 0; ci < cells.size(); ci++) {
                //从第三行第四列开始判断
                if (ri >= 2) {
                    if (ci >= 3) {
                        String cell = cells.get(ci);
                        if (cell != null && cell.trim().length() > 0) {
                            MatAndCost matAndCost = new MatAndCost();
                            String matNr = rows.get(0).get(ci);
                            String matDesc = rows.get(1).get(ci);
                            String costDesc = rows.get(ri).get(1);
                            String costCode = rows.get(ri).get(2);
                            matAndCost.setCostCode(costCode);
                            matAndCost.setCostDesc(costDesc);
                            matAndCost.setMatNr(matNr);
                            matAndCost.setMatDesc(matDesc);
                            matAndCosts.add(matAndCost);
                        }
                    }

                }
            }
            for (MatAndCost matAndCost : matAndCosts) {
                System.out.println(matAndCost);
            }
        }

    }
}
