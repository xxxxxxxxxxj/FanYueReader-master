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
package com.jack.reader.component;

import com.jack.reader.BookListActivity;
import com.jack.reader.ui.activity.SerchBookActivity;
import com.jack.reader.ui.activity.SplashActivity;
import com.jack.reader.ui.activity.SubCategoryListActivity;
import com.jack.reader.ui.activity.SubOtherHomeRankActivity;
import com.jack.reader.ui.activity.SubRankActivity;
import com.jack.reader.ui.activity.SubjectBookListActivity;
import com.jack.reader.ui.activity.SubjectBookListDetailActivity;
import com.jack.reader.ui.activity.TopCategoryListActivity;
import com.jack.reader.ui.activity.TopRankActivity;
import com.jack.reader.ui.fragment.BookrackFragment;
import com.jack.reader.ui.fragment.ClassifyFragment;
import com.jack.reader.ui.fragment.MyFragment;
import com.jack.reader.ui.fragment.RankListFragment;
import com.jack.reader.ui.fragment.SubCategoryFragment;
import com.jack.reader.ui.fragment.SubRankFragment;
import com.jack.reader.ui.fragment.SubjectFragment;

import dagger.Component;

/**
 * @author yuyh.
 * @date 16/9/1.
 */
@Component(dependencies = AppComponent.class)
public interface FindComponent {

    /** 分类 **/
    TopCategoryListActivity inject(TopCategoryListActivity activity);

    SubCategoryListActivity inject(SubCategoryListActivity activity);

    SubCategoryFragment inject(SubCategoryFragment fragment);
    ClassifyFragment inject(ClassifyFragment fragment);
    SplashActivity inject(SplashActivity activity);
    MyFragment inject(MyFragment fragment);
    BookrackFragment inject(BookrackFragment fragment);

    RankListFragment inject(RankListFragment fragment);

    /** 排行 **/
    TopRankActivity inject(TopRankActivity activity);

    SubRankActivity inject(SubRankActivity activity);

    SubOtherHomeRankActivity inject(SubOtherHomeRankActivity activity);

    SubRankFragment inject(SubRankFragment fragment);

    /** 主题书单 **/
    SubjectBookListActivity inject(SubjectBookListActivity subjectBookListActivity);

    SubjectFragment inject(SubjectFragment subjectFragment);

    SubjectBookListDetailActivity inject(SubjectBookListDetailActivity categoryListActivity);
    BookListActivity inject(BookListActivity bookListActivity);
    SerchBookActivity inject(SerchBookActivity serchBookActivity);
}