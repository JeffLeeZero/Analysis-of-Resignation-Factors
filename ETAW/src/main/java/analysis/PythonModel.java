package analysis;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class PythonModel {
    private String aid;
    public PythonModel(String aid){
        this.aid = aid;
    }

    /**
     * 训练模型
     * @param url
     */
    public void trainModel(String url){
        try{
            Process svmProc,logProc;
            String svmUrl = this.getClass().getResource("../logisticregression/svm_train.py").getFile().substring(1);
            System.out.println("svmUrl:"+svmUrl);
            String[] svmProcData = new String[]{"python"
                    , svmUrl, aid, url};
            svmProc = Runtime.getRuntime().exec(svmProcData);
            svmProc.waitFor();
            String regUrl = this.getClass().getResource("../logisticregression/log_reg_train.py").getFile().substring(1);
            System.out.println("regUrl:"+regUrl);
            String[] logProcData = new String[]{"python"
                    , regUrl, aid, url};
            logProc = Runtime.getRuntime().exec(logProcData);

            logProc.waitFor();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 预测离职结果
     * @param data
     * @param department
     * @return
     * [
     *  (模型1)：[判断结果，准确率]，
     * （模型2）：[判断结果，准确率]
     * ]
     */
    public ArrayList<ArrayList<Double>> getProbability(ArrayList<String> data, String department) {
        Process proc;
        ArrayList<String> dataset = new ArrayList<>();
        try{
            String testDatas = String.join(";", data);
            System.out.println(testDatas);

            String url = this.getClass().getResource("../logisticregression/analyze.py").getFile().substring(1);
            String[] fileData = new String[]{"python", url, testDatas,aid,department};


            proc = Runtime.getRuntime().exec(fileData);
            BufferedReader in =  new BufferedReader(new InputStreamReader(proc.getInputStream()));
            String line;
            while ((line = in.readLine()) != null){
                dataset.add(line);
            }
            in.close();
            proc.waitFor();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        System.out.println(dataset);
        ArrayList<ArrayList<Double>> result = new ArrayList<>();
        result.add(new ArrayList<Double>());
        result.add(new ArrayList<Double>());
        int count = 2;
        result.get(0).add(Double.valueOf(dataset.get(0)));
        result.get(0).add(Double.valueOf(dataset.get(1)));
        result.get(1).add(Double.valueOf(dataset.get(count)));
        result.get(1).add(Double.valueOf(dataset.get(count+1)));
        return result;
    }
    /**
     *
     * @param csvURL
     * @return
     *[
     *   [（模型1)：[判断结果，准确率]，
     *   （模型2）：[判断结果，准确率]
     *   ]，
     *   [……],
     *   ……
     *]
     */
    public ArrayList<ArrayList<ArrayList<Double>>> getProbabilityFromCSV(String csvURL){
        Process proc;
        ArrayList<String> dataset = new ArrayList<>();
        try{

            String url = this.getClass().getResource("../logisticregression/analyze_csv.py").getFile().substring(1);
            String[] fileData = new String[]{"python", url, csvURL,aid};

            proc = Runtime.getRuntime().exec(fileData);
            BufferedReader in =  new BufferedReader(new InputStreamReader(proc.getInputStream()));
            String line;
            while ((line = in.readLine()) != null){
                dataset.add(line);
            }
            in.close();
            proc.waitFor();
        }
        catch (Exception e){
            e.printStackTrace();
        }

        /*
        [
            [（模型1)：[判断结果，准确率]，
             （模型2）：[判断结果，准确率]
            ]，
            [……],
            ……
        ]
         */
        ArrayList<ArrayList<ArrayList<Double>>> results = new ArrayList<>();

        //预测员工的人数
        int count = dataset.size()/4;
        //每个模型的数据长度
        int length = dataset.size()/2;
        ArrayList<ArrayList<Double>> result;
        ArrayList<Double> model1;
        ArrayList<Double> model2;
        for(int i = 0;i < count;i++){
            result = new ArrayList<>();
            model1 = new ArrayList<>();
            model2 = new ArrayList<>();
            model1.add(Double.valueOf(dataset.get(i*2)));
            model1.add(Double.valueOf(dataset.get(i*2+1)));
            model2.add(Double.valueOf(dataset.get(i*2+length)));
            model2.add(Double.valueOf(dataset.get(i*2+length+1)));
            result.add(model1);
            result.add(model2);
            results.add(result);
        }
        return results;
    }

    public static void main(String args[]){
        PythonModel py = new PythonModel("afad");
        String url = PythonModel.class.getClass().getResource("../../logisticregression/analyze.py").getFile();
        System.out.println(py.getClass().getResource("../").getFile());
        System.out.println(url);
//        ArrayList<String> data = new ArrayList<>();
//        //'0.38,0.53,157,3,2,0,0,0'
//
////
////
//        PythonModel py = new PythonModel("999");

//        long start = System.currentTimeMillis();
        //py.trainModel("C:\\Users\\west\\Desktop\\Analysis-of-Resignation-Factors\\ETAW\\test.csv");
//        long end = System.currentTimeMillis();
//        System.out.println((end - start)/1000);

//        ArrayList<ArrayList<ArrayList<Double>>> csvResults = py.getProbabilityFromCSV("E:\\LR\\Analysis-of-Resignation-Factors-master\\ETAW\\import_test.csv");
//        System.out.println(csvResults);

//        ArrayList<ArrayList<Double>> result = py.getProbability(data,"IT");
//        System.out.println(result);


    }
}


