package com.ksj.textboard;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        // write your code here
        Scanner sc = new Scanner(System.in);
        ArrayList<Article> articles = new ArrayList<Article>(); // articles라는 이름의 리스트객체 생성
        int idLast = 0;
        Article save = null;

        System.out.println("== 게시판 v 0.1 ==");
        System.out.println("== 프로그램 시작 ==");
        while (true) {
            System.out.print("명령)");
            String text = sc.nextLine();
            System.out.println("입력된 명령어는 : " + text);
            if (text.equals("exit")) {
                break;
            } else if (text.equals("/usr/article/write")) {
                System.out.println("- 게시물 등록 -");
                System.out.print("제목 : ");
                String title = sc.nextLine();
                System.out.print("내용 : ");
                String body = sc.nextLine();
                int id = idLast + 1;
                idLast = id;
                Article article = new Article(id, title, body);
                System.out.println("생성된 게시물 객체 : " + article);
                System.out.printf("%d번 게시물이 등록되었습니다.\n", id);
                save = article; // save 안에 article의 값을 전달(이과정이없으면 if문이 끝나며 값을 까먹음)
                articles.add(save); // 리스트 객체 articles에 save 값을 차곡차곡 저장
            } else if (text.equals("/usr/article/detail")) {
                if (save == null) {
                    System.out.println("등록된 게시글이 없습니다.");
                    continue;
                }
                System.out.println("- 게시물 상세보기 -");
                System.out.println("번호 : " + save.id);
                System.out.println("제목 : " + save.title);
                System.out.println("내용 : " + save.body);
            } else if (text.equals("/usr/article/list")) {
                System.out.println("- 게시물 리스트 -");
                System.out.println("--------------------");
                System.out.println("번호 / 제목");
                System.out.println("--------------------");
                for (int i = articles.size() - 1; i < articles.size() && i >= 0; i--) { // 4 3 2 1 0
                    Article article = articles.get(i);
                    System.out.printf("%d / %s\n", article.id, article.title);
                }
            }

        }

        System.out.println("== 프로그램 종료 ==");
    }
}

class Article {
    int id;
    String title;
    String body;

    Article(int id, String title, String body) // 객체의 형식 지정
    {
        this.id = id;
        this.title = title;
        this.body = body;
    }

    public String toString() {
        return String.format("{게시물 개수 : %d, 제목 : %s, 내용 : %s}", id, title, body);
    }
}