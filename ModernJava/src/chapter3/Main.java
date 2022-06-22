package chapter3;

import chapter3.practice.Phone;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {
        String result = ExecuteAroundPattern.processFile((BufferedReader br) ->
                br.readLine() + br.readLine());
        System.out.println(result);

    }
}
