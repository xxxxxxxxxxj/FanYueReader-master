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
package com.jack.reader.ui.contract;

import com.jack.reader.base.BaseContract;
import com.jack.reader.bean.BannerBean;
import com.jack.reader.bean.BookMixAToc;
import com.jack.reader.bean.ChapterRead;

import java.util.List;

import okhttp3.RequestBody;

/**
 * @author lfh.
 * @date 2016/8/7.
 */
public interface BookReadContract {

    interface View extends BaseContract.BaseView {
        void showBookToc(List<BookMixAToc.mixToc.Chapters> list);

        void showChapterRead(ChapterRead.Chapter data, int chapter);

        void showBannerListError(String msg);

        void showBannerList(List<BannerBean.BannerData.NewBanner> imageList);

        void countreadError(String msg);

        void countreadSuccess();

        void updatamybookError(String msg);

        void updatamybookSuccess();

        void netError(int chapter);//添加网络处理异常接口
    }

    interface Presenter<T> extends BaseContract.BasePresenter<T> {
        void getBookMixAToc(RequestBody body);

        void getChapterRead(RequestBody body, int chapter,String title);

        void getBannerList(RequestBody body);

        void updatamybook(RequestBody body);

        void countread(RequestBody body);
    }
}
