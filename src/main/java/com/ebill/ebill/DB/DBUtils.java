package com.ebill.ebill.DB;

import javafx.scene.control.Alert;

import java.sql.*;

public class DBUtils {

    public static String DB_url = "jdbc:mysql://localhost:3306/javafx-ebill";
    public static String DB_user = "root";
    public static String DB_pass = "Nikhil@123";
    public static String[] Questions = {
            "What is your mother's maiden name?"
            , "What is the name of your first pet?"
            , "Who was your childhood hero?"
            , "What was the name of your high school?"
            , "What was your childhood nickname?"
    };

    public static void signUpUser(String username, String password, int ques_id, String ques_ans) {
        Connection connection = null;
        PreparedStatement psInsert = null;
        PreparedStatement pscheckExists = null;
        ResultSet resultSet = null;

        try {
            connection = DriverManager.getConnection(DB_url, DB_user, DB_pass);
            pscheckExists = connection.prepareStatement("SELECT * FROM users where USERNAME = ?");
            pscheckExists.setString(1, username);
            resultSet = pscheckExists.executeQuery();

            if (resultSet.isBeforeFirst()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Username not available ! \nPlease use alternate username.");
                alert.show();

            } else {
                psInsert = connection.prepareStatement("INSERT INTO users(USERNAME,PASSWORD,QUES_ID,QUES_ANS) VALUES (? ,? ,? ,?)");
                psInsert.setString(1, username);
                psInsert.setString(2, password);
                psInsert.setInt(3, ques_id);
                psInsert.setString(4, ques_ans);
                if (psInsert.executeUpdate() > 0) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText("Account created successfully..");
                    alert.show();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Technical exception occurred !");
                    alert.show();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (pscheckExists != null) {
                try {
                    pscheckExists.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (psInsert != null) {
                try {
                    psInsert.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    public static void loginUser(String uname, String pass) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DriverManager.getConnection(DB_url, DB_user, DB_pass);
            preparedStatement = connection.prepareStatement("SELECT USERNAME,PASSWORD FROM users where USERNAME = ? and PASSWORD = ?");
            preparedStatement.setString(1, uname);
            preparedStatement.setString(2, pass);
            resultSet = preparedStatement.executeQuery();

            if (!resultSet.isBeforeFirst()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Invalid username or password !");
                alert.show();
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Logged in successfully..");
                alert.show();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    public static String[] getUserDetails(String uname, String pass, String ques_id, String ques_ans) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String[] userData = new String[4];

        StringBuilder sqlStmt = new StringBuilder();
        sqlStmt.append("SELECT USERNAME,PASSWORD,QUES_ID,QUES_ANS FROM users where 1 = 1");
        if (uname != null) {
            sqlStmt.append(" AND USERNAME = ?");
        }
        if (pass != null) {
            sqlStmt.append(" AND PASSWORD = ?");
        }
        if (ques_id != null) {
            sqlStmt.append(" AND QUES_ID = ?");
        }
        if (ques_ans != null) {
            sqlStmt.append(" AND QUES_ANS = ?");
        }
        String pstmt = sqlStmt.toString();

        try {
            connection = DriverManager.getConnection(DB_url, DB_user, DB_pass);
            preparedStatement = connection.prepareStatement(pstmt);
            int count = 1;
            if (uname != null) {
                preparedStatement.setString(count, uname);
                count++;
            }
            if (pass != null) {
                preparedStatement.setString(count, pass);
                count++;
            }
            if (ques_id != null) {
                preparedStatement.setInt(count, Integer.parseInt(ques_id));
                count++;
            }
            if (ques_ans != null) {
                preparedStatement.setString(count, ques_ans);
            }

            resultSet = preparedStatement.executeQuery();

            if (!resultSet.isBeforeFirst()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("User doesn't exist !");
                alert.show();
            } else {
                while (resultSet.next()) {
                    userData[0] = resultSet.getNString("USERNAME");
                    userData[1] = resultSet.getNString("PASSWORD");
                    userData[2] = String.valueOf(resultSet.getInt("QUES_ID"));
                    userData[3] = resultSet.getNString("QUES_ANS");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        }
        return userData;
    }

    public static void updateUserPassword(String pass, String uname_in_clause) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = DriverManager.getConnection(DB_url, DB_user, DB_pass);
            preparedStatement = connection.prepareStatement("UPDATE users SET PASSWORD = ? where USERNAME = ? ");
            preparedStatement.setString(1, pass);
            preparedStatement.setString(2, uname_in_clause);
            if (preparedStatement.executeUpdate() > 0) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Password updated successfully..\nPlease login again.");
                alert.show();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Technical exception occurred !");
                alert.show();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
