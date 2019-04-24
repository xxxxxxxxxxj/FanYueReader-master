/**
 * Copyright 2016 JustWayward Team
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.jack.reader.api;

import com.jack.reader.base.Constant;
import com.jack.reader.bean.AutoComplete;
import com.jack.reader.bean.BannerBean;
import com.jack.reader.bean.BookDetail;
import com.jack.reader.bean.BookHelp;
import com.jack.reader.bean.BookHelpList;
import com.jack.reader.bean.BookListDetail;
import com.jack.reader.bean.BookListTags;
import com.jack.reader.bean.BookLists;
import com.jack.reader.bean.BookReview;
import com.jack.reader.bean.BookReviewList;
import com.jack.reader.bean.BookSource;
import com.jack.reader.bean.BooksByCats;
import com.jack.reader.bean.BooksByTag;
import com.jack.reader.bean.CategoryList;
import com.jack.reader.bean.CategoryListLv2;
import com.jack.reader.bean.ChapterBean;
import com.jack.reader.bean.ChapterInfoBean;
import com.jack.reader.bean.ClassIndexBean;
import com.jack.reader.bean.CommentList;
import com.jack.reader.bean.DefaultBean;
import com.jack.reader.bean.DiscussionList;
import com.jack.reader.bean.Disscussion;
import com.jack.reader.bean.HotReview;
import com.jack.reader.bean.HotWord;
import com.jack.reader.bean.KeywordListBean;
import com.jack.reader.bean.MyIndexBean;
import com.jack.reader.bean.NewBookListBean;
import com.jack.reader.bean.PageHomeBean;
import com.jack.reader.bean.RankListBean;
import com.jack.reader.bean.RankingList;
import com.jack.reader.bean.Rankings;
import com.jack.reader.bean.Recommend;
import com.jack.reader.bean.RecommendBookList;
import com.jack.reader.bean.SearchDetail;
import com.jack.reader.bean.user.Login;
import com.jack.reader.bean.user.LoginReq;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Query;
import rx.Completable;
import rx.Observable;

/**
 * https://github.com/JustWayward/BookReader
 *
 * @author yuyh.
 * @date 2016/8/3.
 */
public class BookApi {

    public static BookApi instance;

    private BookApiService service;

    public BookApi(OkHttpClient okHttpClient) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.API_BASE_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create()) // 添加Rx适配器
                .addConverterFactory(GsonConverterFactory.create()) // 添加Gson转换器
                .client(okHttpClient)
                .build();
        service = retrofit.create(BookApiService.class);
    }

    public static BookApi getInstance(OkHttpClient okHttpClient) {
        if (instance == null)
            instance = new BookApi(okHttpClient);
        return instance;
    }

    public Observable<Recommend> getRecommend(String gender) {
        return service.getRecomend(gender);
    }

    public Observable<HotWord> getHotWord() {
        return service.getHotWord();
    }

    public Observable<AutoComplete> getAutoComplete(String query) {
        return service.autoComplete(query);
    }

    public Observable<SearchDetail> getSearchResult(String query) {
        return service.searchBooks(query);
    }

    public Observable<BooksByTag> searchBooksByAuthor(String author) {
        return service.searchBooksByAuthor(author);
    }

    public Observable<BookDetail> getBookDetail(String bookId) {
        return service.getBookDetail(bookId);
    }

    public Observable<HotReview> getHotReview(String book) {
        return service.getHotReview(book);
    }

    public Observable<RecommendBookList> getRecommendBookList(String bookId, String limit) {
        return service.getRecommendBookList(bookId, limit);
    }

    public Observable<BooksByTag> getBooksByTag(String tags, String start, String limit) {
        return service.getBooksByTag(tags, start, limit);
    }

    public synchronized Observable<List<BookSource>> getBookSource(String view, String book) {
        return service.getABookSource(view, book);
    }

    public Observable<RankingList> getRanking() {
        return service.getRanking();
    }

    public Observable<Rankings> getRanking(String rankingId) {
        return service.getRanking(rankingId);
    }

    public Observable<BannerBean> getBannerList(RequestBody body) {
        return service.getBannerList(body);
    }

    public Observable<BookLists> getBookLists(String duration, String sort, String start, String limit, String tag, String gender) {
        return service.getBookLists(duration, sort, start, limit, tag, gender);
    }

    public Observable<BookListTags> getBookListTags() {
        return service.getBookListTags();
    }

    public Observable<BookListDetail> getBookListDetail(String bookListId) {
        return service.getBookListDetail(bookListId);
    }

    public synchronized Observable<CategoryList> getCategoryList() {
        return service.getCategoryList();
    }

    public Observable<CategoryListLv2> getCategoryListLv2() {
        return service.getCategoryListLv2();
    }

    public Observable<BooksByCats> getBooksByCats(String gender, String type, String major, String minor, int start, @Query("limit") int limit) {
        return service.getBooksByCats(gender, type, major, minor, start, limit);
    }

    public Observable<DiscussionList> getBookDisscussionList(String block, String duration, String sort, String type, String start, String limit, String distillate) {
        return service.getBookDisscussionList(block, duration, sort, type, start, limit, distillate);
    }

    public Observable<Disscussion> getBookDisscussionDetail(String disscussionId) {
        return service.getBookDisscussionDetail(disscussionId);
    }

    public Observable<CommentList> getBestComments(String disscussionId) {
        return service.getBestComments(disscussionId);
    }

    public Observable<CommentList> getBookDisscussionComments(String disscussionId, String start, String limit) {
        return service.getBookDisscussionComments(disscussionId, start, limit);
    }

    public Observable<BookReviewList> getBookReviewList(String duration, String sort, String type, String start, String limit, String distillate) {
        return service.getBookReviewList(duration, sort, type, start, limit, distillate);
    }

    public Observable<BookReview> getBookReviewDetail(String bookReviewId) {
        return service.getBookReviewDetail(bookReviewId);
    }

    public Observable<CommentList> getBookReviewComments(String bookReviewId, String start, String limit) {
        return service.getBookReviewComments(bookReviewId, start, limit);
    }

    public Observable<BookHelpList> getBookHelpList(String duration, String sort, String start, String limit, String distillate) {
        return service.getBookHelpList(duration, sort, start, limit, distillate);
    }

    public Observable<BookHelp> getBookHelpDetail(String helpId) {
        return service.getBookHelpDetail(helpId);
    }

    public Observable<Login> login(String platform_uid, String platform_token, String platform_code) {
        LoginReq loginReq = new LoginReq();
        loginReq.platform_code = platform_code;
        loginReq.platform_token = platform_token;
        loginReq.platform_uid = platform_uid;
        return service.login(loginReq);
    }

    public Observable<DiscussionList> getBookDetailDisscussionList(String book, String sort, String type, String start, String limit) {
        return service.getBookDetailDisscussionList(book, sort, type, start, limit);
    }

    public Observable<HotReview> getBookDetailReviewList(String book, String sort, String start, String limit) {
        return service.getBookDetailReviewList(book, sort, start, limit);
    }

    public Observable<DiscussionList> getGirlBookDisscussionList(String block, String duration, String sort, String type, String start, String limit, String distillate) {
        return service.getBookDisscussionList(block, duration, sort, type, start, limit, distillate);
    }

    public Observable<DefaultBean> updatauserinfo(RequestBody body) {
        return service.updatauserinfo(body);
    }

    public Observable<PageHomeBean> pagehome(RequestBody body) {
        return service.pagehome(body);
    }

    public Observable<ClassIndexBean> classIndex(RequestBody body) {
        return service.classIndex(body);
    }

    public Observable<RankListBean> rankList(RequestBody body) {
        return service.rankList(body);
    }

    public Observable<MyIndexBean> myIndex(RequestBody body) {
        return service.myIndex(body);
    }

    public Observable<NewBookListBean> bookListClass(RequestBody body) {
        return service.bookListClass(body);
    }

    public Observable<NewBookListBean> bookListBangDan(RequestBody body) {
        return service.bookListBangDan(body);
    }

    public Observable<NewBookListBean> bookListSerch(RequestBody body) {
        return service.bookListSerch(body);
    }

    public Observable<NewBookListBean> bookListCollect(RequestBody body) {
        return service.bookListCollect(body);
    }

    public Observable<KeywordListBean> keyword(RequestBody body) {
        return service.keyword(body);
    }

    public Observable<ChapterBean> getBookMixAToc(RequestBody body) {
        return service.getBookMixAToc(body);
    }

    public synchronized Observable<ChapterInfoBean> getChapterRead(RequestBody body) {
        return service.getChapterRead(body);
    }

    public Observable<DefaultBean> updatamybook(RequestBody body) {
        return service.updatamybook(body);
    }

    public Observable<DefaultBean> countread(RequestBody body) {
        return service.countread(body);
    }
}
