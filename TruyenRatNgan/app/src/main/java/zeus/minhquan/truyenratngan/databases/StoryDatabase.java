package zeus.minhquan.truyenratngan.databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;
import java.util.List;

import zeus.minhquan.truyenratngan.model.Chapter;
import zeus.minhquan.truyenratngan.model.Story;

/**
 * Created by QuanT on 4/18/2017.
 */

public class StoryDatabase extends SQLiteAssetHelper {
    private static final String DATABASE_NAME = "story.db";
    private static final int DATABASE_VERSION = 1;

    private static final String STORY_TITLE = "title";
    private static final String STORY_ID = "id";
    private static final String STORY_IMAGE = "image";
    private static final String STORY_DESCRIPTION = "description";
    private static final String STORY_IS_FAVORITE = "is_favorite";
    private static final String STORY_LAST_CHAPTER_NO = "last_chapter_no";
    private static final String[] STORY_ALL_COLLUMS = new String[]{STORY_ID,STORY_IMAGE, STORY_TITLE, STORY_DESCRIPTION, STORY_IS_FAVORITE, STORY_LAST_CHAPTER_NO};

    private static final String CHAPTER_TITLE = "title";
    private static final String CHAPTER_ID = "id";
    private static final String CHAPTER_CONTENT = "content";
    private static final String[] CHAPTER_ALL_COLLUMS = new String[]{CHAPTER_ID,CHAPTER_TITLE,CHAPTER_CONTENT};



    public StoryDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public List<Story> loadAllStories() {
        List<Story> stories = new ArrayList<>();
        //Get readable database
        SQLiteDatabase db = getReadableDatabase();
        //Query => cursor
        Cursor cursor = db.query("tbl_story", STORY_ALL_COLLUMS, null, null, null, null, null);
        //Go through rows
        while (cursor.moveToNext()) {
            int id = Integer.parseInt(cursor.getString(cursor.getColumnIndex(STORY_ID)));
            String title = cursor.getString(cursor.getColumnIndex(STORY_TITLE));
            String image = cursor.getString(cursor.getColumnIndex(STORY_IMAGE));
            String description = cursor.getString(cursor.getColumnIndex(STORY_DESCRIPTION));
            boolean isFavorite = cursor.getInt(cursor.getColumnIndex(STORY_IS_FAVORITE)) != 0;
            int lastChapterNo = Integer.parseInt(cursor.getString(cursor.getColumnIndex(STORY_LAST_CHAPTER_NO)));
            Story story = new Story(id,image, title, description, isFavorite, lastChapterNo);
            stories.add(story);

        }
        cursor.close();
        db.close();
        return stories;
    }

    public int getChapterCount(Story story){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT count(novel_id) FROM tbl_chapter WHERE novel_id = ?",new String[]{
                ((Integer)story.getId()).toString()
        });
        cursor.moveToFirst();
        int chapterCount = cursor.getInt(0);
        cursor.close();
        return chapterCount;
    }

    public List<Integer> loadChapterIds(Story story){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT id FROM tbl_chapter WHERE novel_id = ?", new String[]{
                ((Integer)story.getId()).toString()});
        List<Integer> chapterIDs = new ArrayList<>();
        while (cursor.moveToNext()){
            chapterIDs.add(cursor.getInt(0));
        }
        cursor.close();
        return chapterIDs;
    }

    public Chapter getChapter(int chapterID){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query("tbl_chapter",CHAPTER_ALL_COLLUMS,"id=?",new String[]{
            ((Integer)chapterID).toString()},null,null,null);
        cursor.moveToFirst();
        String title = cursor.getString(cursor.getColumnIndex(CHAPTER_TITLE));
        String content = cursor.getString(cursor.getColumnIndex(CHAPTER_CONTENT));
        cursor.close();
        return new Chapter(chapterID,title,content);
    }

    public boolean updateChapterNo(int id, int lastChapter){
        ContentValues data = new ContentValues();
        SQLiteDatabase db = getReadableDatabase();
        data.put(STORY_LAST_CHAPTER_NO,lastChapter);
        return db.update("tbl_story",data,"id= "+id,null) >0;
    }

    public Story getStoryByID(int storyID){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query("tbl_story",STORY_ALL_COLLUMS,"id=?",new String[]{
                ((Integer)storyID).toString()},null,null,null);
        cursor.moveToFirst();
        int id = Integer.parseInt(cursor.getString(cursor.getColumnIndex(STORY_ID)));
        String title = cursor.getString(cursor.getColumnIndex(STORY_TITLE));
        String image = cursor.getString(cursor.getColumnIndex(STORY_IMAGE));
        String description = cursor.getString(cursor.getColumnIndex(STORY_DESCRIPTION));
        boolean isFavorite = cursor.getInt(cursor.getColumnIndex(STORY_IS_FAVORITE)) != 0;
        int lastChapterNo = Integer.parseInt(cursor.getString(cursor.getColumnIndex(STORY_LAST_CHAPTER_NO)));
        Story story = new Story(id,image, title, description, isFavorite, lastChapterNo);
        cursor.close();
        return story;
    }
}
