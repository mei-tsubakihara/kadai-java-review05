package jp.co.kiramex.dbSample.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Review05 {

    public static void main(String[] args) {
        //3.データベース接続と結果取得のための変数宣言
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            //1.ドライバのクラスをjava上で読み込む
            Class.forName("com.mysql.cj.jdbc.Driver");

            //2.DBと接続する
            con = DriverManager.getConnection(
                    "jdbc:mysql://localhost/kadaidb?useSSL=false&allowPublicKeyRetrieval=true",
                    "root",
                    "meitsubakihara0519"
   );
            //4.DBとやりとりする窓口（prepareStatementオブジェクトの作成）
            String sql = "SELECT * FROM person WHERE id = ?";
            pstmt = con.prepareStatement(sql);

            //5,6.Select文の実行と結果を格納/代入
            System.out.println("検索するキーワードを入力してください >");
            int input = keyIn();

            //PreparedStatmentオブジェクトの？に値をセット
            pstmt.setInt(1,  input);
            rs = pstmt.executeQuery();

            //7.結果を表示する
            while(rs.next()) {
                String name = rs.getString("name");
                int age = rs.getInt("age");
                System.out.println(name);
                System.out.println(age);
            }


        } catch (ClassNotFoundException e) {
            System.err.println("JDBCドライバのロードに失敗しました");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("データベースに異常が発生しました");
            e.printStackTrace();
        } finally {

        //8.接続を閉じる
        if(rs != null) {
            try {
                rs.close();
            }catch(SQLException e) {
                System.err.println("ResultSetを閉じるときにエラーが発生しました");
                e.printStackTrace();
            }
        }
        if(pstmt != null) {
            try {
                pstmt.close();
            }catch(SQLException e) {
                System.err.println("PreparedStatementを閉じるときにエラーが発生しました");
                e.printStackTrace();
            }
        }
            if( con != null) {
            try {
                con.close();
            } catch(SQLException e) {
                System.err.println("データベース切断時にエラーが発生しました");
                e.printStackTrace();
            }
        }

    }

    }

    /*
     * キーボードから入力された値をintで返す　引数：なし　戻り値：int
     */
     private static int keyIn() {
         int line = 0;
         try {
             BufferedReader key = new BufferedReader(new InputStreamReader(System.in));
             line = Integer.parseInt(key.readLine());
         }catch(IOException e) {
     }
         return line;
     }

}
