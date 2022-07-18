import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class Algorithm0001 {

//    有n个动物重量分别a1，a2，a3....an,
//
//    这群动物一起玩叠罗汉游戏
//
//    规定从左往右选择动物，每只动物左边动物的总重量不超过自己的重量
//
//    返回最多能选多少个动物，求一个高效的算法
//
//    比如有7个动物，从左往右重量依次为 1，3，5，7，9，11，21
//
//    最多能选5个动物 1，3，5，9，21
//
//    注意：
//
//            - 实际给定的数组可能是无序的
//            - 要求从左往右选动物，且不能打乱原始数组

    public static int[] maxAnimal(int[] srcArray) {
        int index = 0; // 按顺序从第一个动物开始

        // 存储累计重量
        int[] ends = new int[srcArray.length];

        // 存储纳入的动物重量
        int[] valids = new int[srcArray.length];
        // 纳入的动物数量
        int validCount = 0;

        // 0- index, 1-validCount
        int[] opt = new int[2];
        opt[0] = index;
        opt[1] = validCount;

        conquer(srcArray, valids, ends, opt);

        index = opt[0];
        validCount = opt[1];
        int[] mArray = new int[validCount];
        for (int i = 0; i < validCount; i++) {
            mArray[i] = valids[i];
        }
        return mArray;
    }

    public static void conquer(int[] srcArray, int[] valids, int[] ends, int[] opt) {
        int index = opt[0];
        int validCount = opt[1];
        if (index < srcArray.length) {
            if (index == 0) {
                valids[validCount] = srcArray[index];
                ends[validCount] = srcArray[index];
                validCount++;

            } else {
                if (ends[validCount - 1] <= srcArray[index]) {
                    valids[validCount] = srcArray[index];
                    ends[validCount] = ends[validCount - 1] + srcArray[index];
                    validCount++;

                } else if (ends[validCount - 1] > srcArray[index]) {
                    if (validCount >= 2) {
                        // 比较累计重量数组 倒数第二项的累计重量+当前索引的动物重量 与 最后一项累计重量 的大小
                        if (ends[validCount - 1] > ends[validCount - 2] + srcArray[index]) {
                            // 说明 当前索引的动物 与 当前已纳入的最后一只动物 相比， 替换已纳入的最后一只动物 更好
                            valids[validCount - 1] = srcArray[index];
                            ends[validCount - 1] = ends[validCount - 2] + srcArray[index];
                        }
                        // else 说明 当前索引的动物 与 当前已纳入的最后一只动物 相比， 保持当前 已纳入的动物 更好， 继续索引下一只
                    } else {
                        // 当前只有1只动物纳入， 但是当前索引的动物重量 比 已纳入的唯一动物重量 小，应该把小的重量的动物纳入，替换掉原来的
                        valids[validCount - 1] = srcArray[index];
                        ends[validCount - 1] = srcArray[index];
                    }
                }
            }
            opt[0] = ++index;
            opt[1] = validCount;
            conquer(srcArray, valids, ends, opt);
        }
    }

    public static void main(String[] args) {
        int n = 1000;
        // 随机一些数
        List<Integer> mArrayList = new ArrayList();
        for (int i = 0; i < n; i++) {
            mArrayList.add((int) (Math.random() * 2 * n) + 1);
        }
        mArrayList.sort(new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                return (Integer) o1 - (Integer) o2;
            }
        });
        int[] array = new int[n];
        for (int i = 0; i < n; i++) {
            array[i] = mArrayList.get(i);
        }
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i]);
            System.out.print("  ");
            if (i % 10 == 0 && i > 0) {
                System.out.print("\n");
            }
        }

        System.out.print("\n");
        System.out.print("\n");

        int[] mArray = maxAnimal(array);
        for (int i = 0; i < mArray.length; i++) {
            System.out.print(mArray[i]);
            System.out.print("  ");
            if (i % 10 == 0 && i > 0) {
                System.out.print("\n");
            }
        }
    }
}
