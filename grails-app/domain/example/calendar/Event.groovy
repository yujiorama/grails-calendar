package example.calendar

import org.joda.time.DateTime
import org.joda.time.Minutes

class Event {

  String title
  String location
  String description

  Date startTime
  Date endTime
  
  boolean isRecurring = false
  EventRecurType recurType
  Integer recurInterval = 1

  Event sourceEvent

  static hasMany = [recurDaysOfWeek: Integer, excludeDays: Date]
  static transients = ['durationMintes']

  static constraints = {
    title(nullable: false, blank: false)
    location(nullable: true, blank: true)
    description(nullable: true, blank: true)
    recurType(nullable: true)
    recurUntil(nullable: true)
    recurCount(nullable:true)
    startTime(nullable: false)
    excludeDays(nullable: true)
    sourceEvent(nullable: true)
    startTime(required: true, nullable: false)
    endTime(required: true, nullable: false, validtor: {val, obj -> val > obj.startTime})
    recurDaysOfWeek(validtor: {val, obj ->
      if (obj.recurType == EventRecurType.WEEKLY && ! val) { return 'null' }
                    })
  }

  public int getDurationMinutes() {
    Minutes.minutesBetween(new DateTime(startTime), new DateTime(endTime)).minutes
  }
  
  public void addToRecurDaysOfWeek(Integer daysOfWeek) {
    if (recurDaysOfWeek == null) {
      recurDaysOfWeek = []
    }
    recurDaysOfWeek.add(daysOfWeek)
  }

  public void addToExcludeDays(Date dateTime) {
    if (excludeDays == null) {
      excludeDays = []
    }
    excludeDays.add(dateTime)
  }

  Date recurUntil
  Integer recurCount
}

public enum EventRecurType {
  DAILY('Daily'),
  WEEKLY('Weekly'),
  MONTHLY('Monthly'),
  YEARLY('Yearly')
  
  String name

  EventRecurType(String name) {
    this.name = name
  }
}
