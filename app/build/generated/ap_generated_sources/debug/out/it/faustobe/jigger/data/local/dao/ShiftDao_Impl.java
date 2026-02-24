package it.faustobe.jigger.data.local.dao;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import it.faustobe.jigger.data.local.entities.Shift;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Long;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;

@SuppressWarnings({"unchecked", "deprecation"})
public final class ShiftDao_Impl implements ShiftDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Shift> __insertionAdapterOfShift;

  private final EntityDeletionOrUpdateAdapter<Shift> __updateAdapterOfShift;

  private final SharedSQLiteStatement __preparedStmtOfDeleteById;

  public ShiftDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfShift = new EntityInsertionAdapter<Shift>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `shifts` (`id`,`bartenderId`,`startTime`,`endTime`,`status`,`notes`) VALUES (?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement, final Shift entity) {
        if (entity.getId() == null) {
          statement.bindNull(1);
        } else {
          statement.bindString(1, entity.getId());
        }
        if (entity.getBartenderId() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getBartenderId());
        }
        statement.bindLong(3, entity.getStartTime());
        if (entity.getEndTime() == null) {
          statement.bindNull(4);
        } else {
          statement.bindLong(4, entity.getEndTime());
        }
        if (entity.getStatus() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getStatus());
        }
        if (entity.getNotes() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getNotes());
        }
      }
    };
    this.__updateAdapterOfShift = new EntityDeletionOrUpdateAdapter<Shift>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `shifts` SET `id` = ?,`bartenderId` = ?,`startTime` = ?,`endTime` = ?,`status` = ?,`notes` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement, final Shift entity) {
        if (entity.getId() == null) {
          statement.bindNull(1);
        } else {
          statement.bindString(1, entity.getId());
        }
        if (entity.getBartenderId() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getBartenderId());
        }
        statement.bindLong(3, entity.getStartTime());
        if (entity.getEndTime() == null) {
          statement.bindNull(4);
        } else {
          statement.bindLong(4, entity.getEndTime());
        }
        if (entity.getStatus() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getStatus());
        }
        if (entity.getNotes() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getNotes());
        }
        if (entity.getId() == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.getId());
        }
      }
    };
    this.__preparedStmtOfDeleteById = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM shifts WHERE id = ?";
        return _query;
      }
    };
  }

  @Override
  public void insert(final Shift shift) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfShift.insert(shift);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void update(final Shift shift) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfShift.handle(shift);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteById(final String shiftId) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteById.acquire();
    int _argIndex = 1;
    if (shiftId == null) {
      _stmt.bindNull(_argIndex);
    } else {
      _stmt.bindString(_argIndex, shiftId);
    }
    try {
      __db.beginTransaction();
      try {
        _stmt.executeUpdateDelete();
        __db.setTransactionSuccessful();
      } finally {
        __db.endTransaction();
      }
    } finally {
      __preparedStmtOfDeleteById.release(_stmt);
    }
  }

  @Override
  public Shift getActiveShift() {
    final String _sql = "SELECT * FROM shifts WHERE status = 'ACTIVE' LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfBartenderId = CursorUtil.getColumnIndexOrThrow(_cursor, "bartenderId");
      final int _cursorIndexOfStartTime = CursorUtil.getColumnIndexOrThrow(_cursor, "startTime");
      final int _cursorIndexOfEndTime = CursorUtil.getColumnIndexOrThrow(_cursor, "endTime");
      final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
      final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
      final Shift _result;
      if (_cursor.moveToFirst()) {
        _result = new Shift();
        final String _tmpId;
        if (_cursor.isNull(_cursorIndexOfId)) {
          _tmpId = null;
        } else {
          _tmpId = _cursor.getString(_cursorIndexOfId);
        }
        _result.setId(_tmpId);
        final String _tmpBartenderId;
        if (_cursor.isNull(_cursorIndexOfBartenderId)) {
          _tmpBartenderId = null;
        } else {
          _tmpBartenderId = _cursor.getString(_cursorIndexOfBartenderId);
        }
        _result.setBartenderId(_tmpBartenderId);
        final long _tmpStartTime;
        _tmpStartTime = _cursor.getLong(_cursorIndexOfStartTime);
        _result.setStartTime(_tmpStartTime);
        final Long _tmpEndTime;
        if (_cursor.isNull(_cursorIndexOfEndTime)) {
          _tmpEndTime = null;
        } else {
          _tmpEndTime = _cursor.getLong(_cursorIndexOfEndTime);
        }
        _result.setEndTime(_tmpEndTime);
        final String _tmpStatus;
        if (_cursor.isNull(_cursorIndexOfStatus)) {
          _tmpStatus = null;
        } else {
          _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
        }
        _result.setStatus(_tmpStatus);
        final String _tmpNotes;
        if (_cursor.isNull(_cursorIndexOfNotes)) {
          _tmpNotes = null;
        } else {
          _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
        }
        _result.setNotes(_tmpNotes);
      } else {
        _result = null;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public LiveData<Shift> getActiveShiftLive() {
    final String _sql = "SELECT * FROM shifts WHERE status = 'ACTIVE' LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[] {"shifts"}, false, new Callable<Shift>() {
      @Override
      @Nullable
      public Shift call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfBartenderId = CursorUtil.getColumnIndexOrThrow(_cursor, "bartenderId");
          final int _cursorIndexOfStartTime = CursorUtil.getColumnIndexOrThrow(_cursor, "startTime");
          final int _cursorIndexOfEndTime = CursorUtil.getColumnIndexOrThrow(_cursor, "endTime");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final Shift _result;
          if (_cursor.moveToFirst()) {
            _result = new Shift();
            final String _tmpId;
            if (_cursor.isNull(_cursorIndexOfId)) {
              _tmpId = null;
            } else {
              _tmpId = _cursor.getString(_cursorIndexOfId);
            }
            _result.setId(_tmpId);
            final String _tmpBartenderId;
            if (_cursor.isNull(_cursorIndexOfBartenderId)) {
              _tmpBartenderId = null;
            } else {
              _tmpBartenderId = _cursor.getString(_cursorIndexOfBartenderId);
            }
            _result.setBartenderId(_tmpBartenderId);
            final long _tmpStartTime;
            _tmpStartTime = _cursor.getLong(_cursorIndexOfStartTime);
            _result.setStartTime(_tmpStartTime);
            final Long _tmpEndTime;
            if (_cursor.isNull(_cursorIndexOfEndTime)) {
              _tmpEndTime = null;
            } else {
              _tmpEndTime = _cursor.getLong(_cursorIndexOfEndTime);
            }
            _result.setEndTime(_tmpEndTime);
            final String _tmpStatus;
            if (_cursor.isNull(_cursorIndexOfStatus)) {
              _tmpStatus = null;
            } else {
              _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
            }
            _result.setStatus(_tmpStatus);
            final String _tmpNotes;
            if (_cursor.isNull(_cursorIndexOfNotes)) {
              _tmpNotes = null;
            } else {
              _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            }
            _result.setNotes(_tmpNotes);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Shift getShiftById(final String shiftId) {
    final String _sql = "SELECT * FROM shifts WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (shiftId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, shiftId);
    }
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfBartenderId = CursorUtil.getColumnIndexOrThrow(_cursor, "bartenderId");
      final int _cursorIndexOfStartTime = CursorUtil.getColumnIndexOrThrow(_cursor, "startTime");
      final int _cursorIndexOfEndTime = CursorUtil.getColumnIndexOrThrow(_cursor, "endTime");
      final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
      final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
      final Shift _result;
      if (_cursor.moveToFirst()) {
        _result = new Shift();
        final String _tmpId;
        if (_cursor.isNull(_cursorIndexOfId)) {
          _tmpId = null;
        } else {
          _tmpId = _cursor.getString(_cursorIndexOfId);
        }
        _result.setId(_tmpId);
        final String _tmpBartenderId;
        if (_cursor.isNull(_cursorIndexOfBartenderId)) {
          _tmpBartenderId = null;
        } else {
          _tmpBartenderId = _cursor.getString(_cursorIndexOfBartenderId);
        }
        _result.setBartenderId(_tmpBartenderId);
        final long _tmpStartTime;
        _tmpStartTime = _cursor.getLong(_cursorIndexOfStartTime);
        _result.setStartTime(_tmpStartTime);
        final Long _tmpEndTime;
        if (_cursor.isNull(_cursorIndexOfEndTime)) {
          _tmpEndTime = null;
        } else {
          _tmpEndTime = _cursor.getLong(_cursorIndexOfEndTime);
        }
        _result.setEndTime(_tmpEndTime);
        final String _tmpStatus;
        if (_cursor.isNull(_cursorIndexOfStatus)) {
          _tmpStatus = null;
        } else {
          _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
        }
        _result.setStatus(_tmpStatus);
        final String _tmpNotes;
        if (_cursor.isNull(_cursorIndexOfNotes)) {
          _tmpNotes = null;
        } else {
          _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
        }
        _result.setNotes(_tmpNotes);
      } else {
        _result = null;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<Shift> getRecentShifts(final int limit) {
    final String _sql = "SELECT * FROM shifts ORDER BY startTime DESC LIMIT ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, limit);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfBartenderId = CursorUtil.getColumnIndexOrThrow(_cursor, "bartenderId");
      final int _cursorIndexOfStartTime = CursorUtil.getColumnIndexOrThrow(_cursor, "startTime");
      final int _cursorIndexOfEndTime = CursorUtil.getColumnIndexOrThrow(_cursor, "endTime");
      final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
      final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
      final List<Shift> _result = new ArrayList<Shift>(_cursor.getCount());
      while (_cursor.moveToNext()) {
        final Shift _item;
        _item = new Shift();
        final String _tmpId;
        if (_cursor.isNull(_cursorIndexOfId)) {
          _tmpId = null;
        } else {
          _tmpId = _cursor.getString(_cursorIndexOfId);
        }
        _item.setId(_tmpId);
        final String _tmpBartenderId;
        if (_cursor.isNull(_cursorIndexOfBartenderId)) {
          _tmpBartenderId = null;
        } else {
          _tmpBartenderId = _cursor.getString(_cursorIndexOfBartenderId);
        }
        _item.setBartenderId(_tmpBartenderId);
        final long _tmpStartTime;
        _tmpStartTime = _cursor.getLong(_cursorIndexOfStartTime);
        _item.setStartTime(_tmpStartTime);
        final Long _tmpEndTime;
        if (_cursor.isNull(_cursorIndexOfEndTime)) {
          _tmpEndTime = null;
        } else {
          _tmpEndTime = _cursor.getLong(_cursorIndexOfEndTime);
        }
        _item.setEndTime(_tmpEndTime);
        final String _tmpStatus;
        if (_cursor.isNull(_cursorIndexOfStatus)) {
          _tmpStatus = null;
        } else {
          _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
        }
        _item.setStatus(_tmpStatus);
        final String _tmpNotes;
        if (_cursor.isNull(_cursorIndexOfNotes)) {
          _tmpNotes = null;
        } else {
          _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
        }
        _item.setNotes(_tmpNotes);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public LiveData<List<Shift>> getAllShiftsLive() {
    final String _sql = "SELECT * FROM shifts ORDER BY startTime DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[] {"shifts"}, false, new Callable<List<Shift>>() {
      @Override
      @Nullable
      public List<Shift> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfBartenderId = CursorUtil.getColumnIndexOrThrow(_cursor, "bartenderId");
          final int _cursorIndexOfStartTime = CursorUtil.getColumnIndexOrThrow(_cursor, "startTime");
          final int _cursorIndexOfEndTime = CursorUtil.getColumnIndexOrThrow(_cursor, "endTime");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final List<Shift> _result = new ArrayList<Shift>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Shift _item;
            _item = new Shift();
            final String _tmpId;
            if (_cursor.isNull(_cursorIndexOfId)) {
              _tmpId = null;
            } else {
              _tmpId = _cursor.getString(_cursorIndexOfId);
            }
            _item.setId(_tmpId);
            final String _tmpBartenderId;
            if (_cursor.isNull(_cursorIndexOfBartenderId)) {
              _tmpBartenderId = null;
            } else {
              _tmpBartenderId = _cursor.getString(_cursorIndexOfBartenderId);
            }
            _item.setBartenderId(_tmpBartenderId);
            final long _tmpStartTime;
            _tmpStartTime = _cursor.getLong(_cursorIndexOfStartTime);
            _item.setStartTime(_tmpStartTime);
            final Long _tmpEndTime;
            if (_cursor.isNull(_cursorIndexOfEndTime)) {
              _tmpEndTime = null;
            } else {
              _tmpEndTime = _cursor.getLong(_cursorIndexOfEndTime);
            }
            _item.setEndTime(_tmpEndTime);
            final String _tmpStatus;
            if (_cursor.isNull(_cursorIndexOfStatus)) {
              _tmpStatus = null;
            } else {
              _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
            }
            _item.setStatus(_tmpStatus);
            final String _tmpNotes;
            if (_cursor.isNull(_cursorIndexOfNotes)) {
              _tmpNotes = null;
            } else {
              _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            }
            _item.setNotes(_tmpNotes);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public List<Shift> getShiftsByDateRange(final long startDate, final long endDate) {
    final String _sql = "SELECT * FROM shifts WHERE startTime >= ? AND startTime <= ? ORDER BY startTime DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, startDate);
    _argIndex = 2;
    _statement.bindLong(_argIndex, endDate);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfBartenderId = CursorUtil.getColumnIndexOrThrow(_cursor, "bartenderId");
      final int _cursorIndexOfStartTime = CursorUtil.getColumnIndexOrThrow(_cursor, "startTime");
      final int _cursorIndexOfEndTime = CursorUtil.getColumnIndexOrThrow(_cursor, "endTime");
      final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
      final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
      final List<Shift> _result = new ArrayList<Shift>(_cursor.getCount());
      while (_cursor.moveToNext()) {
        final Shift _item;
        _item = new Shift();
        final String _tmpId;
        if (_cursor.isNull(_cursorIndexOfId)) {
          _tmpId = null;
        } else {
          _tmpId = _cursor.getString(_cursorIndexOfId);
        }
        _item.setId(_tmpId);
        final String _tmpBartenderId;
        if (_cursor.isNull(_cursorIndexOfBartenderId)) {
          _tmpBartenderId = null;
        } else {
          _tmpBartenderId = _cursor.getString(_cursorIndexOfBartenderId);
        }
        _item.setBartenderId(_tmpBartenderId);
        final long _tmpStartTime;
        _tmpStartTime = _cursor.getLong(_cursorIndexOfStartTime);
        _item.setStartTime(_tmpStartTime);
        final Long _tmpEndTime;
        if (_cursor.isNull(_cursorIndexOfEndTime)) {
          _tmpEndTime = null;
        } else {
          _tmpEndTime = _cursor.getLong(_cursorIndexOfEndTime);
        }
        _item.setEndTime(_tmpEndTime);
        final String _tmpStatus;
        if (_cursor.isNull(_cursorIndexOfStatus)) {
          _tmpStatus = null;
        } else {
          _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
        }
        _item.setStatus(_tmpStatus);
        final String _tmpNotes;
        if (_cursor.isNull(_cursorIndexOfNotes)) {
          _tmpNotes = null;
        } else {
          _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
        }
        _item.setNotes(_tmpNotes);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public int getShiftCount() {
    final String _sql = "SELECT COUNT(*) FROM shifts";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _result;
      if (_cursor.moveToFirst()) {
        _result = _cursor.getInt(0);
      } else {
        _result = 0;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
