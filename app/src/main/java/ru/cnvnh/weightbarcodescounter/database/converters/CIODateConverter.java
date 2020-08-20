package ru.cnvnh.weightbarcodescounter.database.converters;

import androidx.annotation.Nullable;
import androidx.room.TypeConverter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CIODateConverter
{
	@TypeConverter
	public static Date timestampToDate(@Nullable Long timestamp)
	{
		return timestamp == null ? null : new Date(timestamp);
	}
	
	@TypeConverter
	public static String timestampToString(@Nullable Long timestamp)
	{
		return timestamp == null ? null : (new SimpleDateFormat("dd.MM.yyyy  HH:mm", Locale.US)).format(new Date(timestamp));
	}
	
	@TypeConverter
	public static Long dateToTimestamp(@Nullable Date date)
	{
		return date == null ? null : date.getTime();
	}
	
	@TypeConverter
	public static String dateToString(@Nullable Date date)
	{
		return date == null ? null : (new SimpleDateFormat("dd.MM.yyyy", Locale.US)).format(date);
	}
}
