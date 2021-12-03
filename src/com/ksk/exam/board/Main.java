package com.ksk.exam.board;

import java.util.*;

public class Main {
  static int articlesLastId = 0;
  static void makeTestData(List<Article> articles) {
    for (int i = 0; i < 32; i++) {
      int id = i + 1;
      articles.add(new Article(id, "제목" + id, "내용" + id));
    }
  }

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    System.out.println("== 게시판 v 0.1 ==");
    System.out.println("== 프로그램 시작 ==");


    List<Article> articles = new ArrayList<>();
    makeTestData(articles);

    if (articles.size() > 0) {
      articlesLastId = articles.get(articles.size() - 1).id;
    }

    while (true) {
      System.out.printf("명령) ");
      String cmd = sc.nextLine();

      Rq rq = new Rq(cmd);
      Map<String, String> params = rq.getParams();

      if (rq.getUrlPath().equals("exit")) {
        break;
      } else if (rq.getUrlPath().equals("/usr/article/list")) {
        actionList(rq, articles);
      } else if (rq.getUrlPath().equals("/usr/article/detail")) {
        actionDetail(rq, articles);
      } else if (rq.getUrlPath().equals("/usr/article/write")) {
       actionWrite(sc, articles);
      } else {
        System.out.printf("입력된 명령어 : %s\n", cmd);
      }
    }

    System.out.println("== 프로그램 종료 ==");

    sc.close();
  }

  private static void actionWrite(Scanner sc, List<Article> articles) {
    System.out.println("- 게시물 등록 -");
    System.out.printf("제목 : ");
    String title = sc.nextLine();
    System.out.printf("내용 : ");
    String body = sc.nextLine();
    int id = articlesLastId + 1;
    articlesLastId = id;

    Article article = new Article(id, title, body);
    articles.add(article);
    System.out.println("생성된 게시물 객체 : " + article);

    System.out.printf("%d번 게시물이 입력되었습니다.\n", article.id);
  }

  private static void actionDetail(Rq rq, List<Article> articles) {
    Map<String, String> params = rq.getParams();
    if (params.containsKey("id") == false) {
      System.out.println("id를 입력해주세요.");
      return;
    }

    int id = 0;

    try {
      id = Integer.parseInt(params.get("id"));
    } catch (NumberFormatException e) {
      System.out.println("id를 정수형태로 입력해주세요.");
      return;
    }

    if (id > articles.size()) {
      System.out.println("게시물이 존재하지 않습니다.");
      return;
    }

    Article article = articles.get(id - 1);

    System.out.println("- 게시물 상세내용 -");
    System.out.printf("번호 : %d\n", article.id);
    System.out.printf("제목 : %s\n", article.title);
    System.out.printf("내용 : %s\n", article.body);
  }

  private static void actionList(Rq rq, List<Article> list) {
    System.out.println("- 게시물 리스트 -");
    System.out.println("--------------------");
    System.out.println("번호 / 제목");
    System.out.println("--------------------");
    List<Article> articles = list;
    Map<String, String> params = rq.getParams();
    if (params.containsKey("page")) {
      int num = Integer.parseInt(params.get("page")); // 2
      int value = num * 10; // 20
      int count = value - 10;
      int size = articles.size() - 1; // 29
      int go = 0;
      if (value <= size + 1) { // 30<=30, 게시글 충분
        List<Article> articlesReserve;
        articlesReserve = Util.reverseList(articles);
        while (go < 10) {
          Article article = articlesReserve.get(count);
          System.out.printf("%d / %s\n", article.id, article.title);
          count++;
          go++;
        }
      } else {List<Article> articlesReserve;
        articlesReserve = Util.reverseList(articles);
        while (go < articles.size()%10) {
          Article article = articlesReserve.get(count);
          System.out.printf("%d / %s\n", article.id, article.title);
          count++;
          go++;
        }
      }
    }

    if (params.containsKey("searchKeyword")) {
      String text = params.get("searchKeyword"); // 값을 text에 저장
      for (int i = articles.size() - 1; i >= 0; i--) {
        Article article = articles.get(i);
        // article변수에 i번째 리스트에있는 내용넣기
        if (article.title.contains(text) || article.body.contains(text)) {
          List<Article> filteredArticles = new ArrayList<>();
          filteredArticles.add(article);
          for (int j = 0; j < filteredArticles.size(); j++) {
            Article articleFilter = filteredArticles.get(j);
            System.out.printf("%d / %s\n", articleFilter.id, articleFilter.title);
          }
        }
      }
    }
    if (params.containsKey("orderBy") && params.get("orderBy").equals("idAsc")) {
      List<Article> articlesReserve;
      articlesReserve = Util.reverseList(articles);
      for (int i = articles.size() - 1; i >= 0; i--) {
        Article article = articlesReserve.get(i);
        System.out.printf("%d / %s\n", article.id, article.title);
      }
    }
    else{
      for (int i = articles.size() - 1; i >= 0; i--) { // 기본+Desc
        Article article = articles.get(i);
        System.out.printf("%d / %s\n", article.id, article.title);
      }
    }

  }
}

// 수정불가능
