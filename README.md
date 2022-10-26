# truthTable
真值表计算器

1.流程图

  ![image](https://user-images.githubusercontent.com/54694818/198082067-de6a3663-3c68-45be-8298-89e037946abc.png)

2.核心代码段分析
	a)中缀转后缀，存入栈中
```
public static void rpn(Stack<Character> operators, Stack output, String str) {
        char[] chars = str.toCharArray();
        int pre = 0;
        boolean digital; //是否为数字（只要不是运算符，都是数字），用于截取字符串
        int len = chars.length;
        int bracket = 0; // 左括号的数量

        for (int i = 0; i < len; ) {
            pre = i;
            digital = Boolean.FALSE;
            //截取数字
            while (i < len && !Operator.isOperator(chars[i])) {
                i++;
                digital = Boolean.TRUE;
            }

            if (digital) {
                output.push(str.substring(pre, i));
                set.add(str.substring(pre, i));
            } else {
                char o = chars[i++]; //运算符
                if (o == '(') {
                    bracket++;
                }
                if (bracket > 0) {
                    if (o == ')') {
                        while (!operators.empty()) {
                            char top = operators.pop();
                            if (top == '(') {
                                break;
                            }
                            output.push(top);
                        }
                        bracket--;
                    } else {
                        //如果栈顶为 ( ，则直接添加，不顾其优先级
                        //如果之前有 ( ，但是 ( 不在栈顶，则需判断其优先级，如果优先级比栈顶的低，则依次出栈
                        while (!operators.empty() && operators.peek() != '(' && Operator.cmp(o, operators.peek()) <= 0) {
                            output.push(operators.pop());
                        }
                        operators.push(o);
                    }
                } else {
                    while (!operators.empty() && Operator.cmp(o, operators.peek()) <= 0) {
                        output.push(operators.pop());
                    }
                    operators.push(o);
                }
            }
        }
        //遍历结束，将运算符栈全部压入output
        while (!operators.empty()) {
            output.push(operators.pop());
        }
    }
}

enum Operator {
    EQUAL('↔', 1),
    BELONG('→', 2),
    OR('∨', 3),
    AND('∧', 4),
    NOT('﹁', 5),
    LEFT_BRACKET('(', 6), RIGHT_BRACKET(')', 6); //括号优先级最高
    char value;
    int priority;

    Operator(char value, int priority) {
        this.value = value;
        this.priority = priority;
    }

    /**
     * 比较两个符号的优先级
     *
     * @param c1
     * @param c2
     * @return c1的优先级是否比c2的高，高则返回正数，等于返回0，小于返回负数
     */
    public static int cmp(char c1, char c2) {
        int p1 = 0;
        int p2 = 0;
        for (Operator o : Operator.values()) {
            if (o.value == c1) {
                p1 = o.priority;
            }
            if (o.value == c2) {
                p2 = o.priority;
            }
        }
        return p1 - p2;
    }

    /**
     * 枚举出来的才视为运算符，用于扩展
     *
     * @param c
     * @return
     */
    public static boolean isOperator(char c) {
        for (Operator o : Operator.values()) {
            if (o.value == c) {
                return true;
            }
        }
        return false;
    }
}
```

b)全排列
```
public static void translation(Object[] objects, Map<Object, Integer> map, int ind) {
    if(ind == objects.length) {
        allNum(map);
        return;
    }
    if (map.containsKey(objects[ind])) {
        translation(objects, map, ind + 1);
    } else {
        for(int i = 0; i < 2; i++){
            map.put(objects[ind], i);
            translation(objects, map, ind + 1);
            map.remove(objects[ind]);
        }
    }
}
```

3.结果

a)

 ![image](https://user-images.githubusercontent.com/54694818/198082142-f5e11472-0c21-41e0-8e03-083c675248a0.png)

b)

![image](https://user-images.githubusercontent.com/54694818/198082163-85036296-5625-41dc-b28e-86781b2dd9fb.png)

c)

 ![image](https://user-images.githubusercontent.com/54694818/198082186-06433ffa-a003-491c-951c-29820f8fef3d.png)

4.选做题二

a)	核心代码
```
XYSeries series = new XYSeries("xySeries");
for (double x = 0; x < 8; x = x + a) {
    double y = -2 * x * Math.sin(x * x);
    series.add(x, y);
}
XYSeriesCollection datasetCollection = new XYSeriesCollection();
datasetCollection.addSeries(series);
JFreeChart chart = ChartFactory.createXYLineChart("", "", "", datasetCollection, PlotOrientation.VERTICAL, false, false, false);
XYPlot plot = (XYPlot) chart.getPlot();
XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
renderer.setSeriesLinesVisible(0, false);
plot.setRenderer(renderer);
ChartFrame frame = new ChartFrame("", chart);
frame.pack();
frame.setVisible(true);
frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
```

b)效果演示
 ![image](https://user-images.githubusercontent.com/54694818/198082240-401cf45a-25cf-4533-ae30-6f39545c3489.png)


