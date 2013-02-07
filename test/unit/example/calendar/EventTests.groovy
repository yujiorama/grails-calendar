package example.calendar

import grails.test.*

import org.joda.time.DateTime
import static org.joda.time.DateTimeConstants.MONDAY
import static org.joda.time.DateTimeConstants.WEDNESDAY
import static org.joda.time.DateTimeConstants.FRIDAY

class EventTests extends GrailsUnitTestCase {

  def now = new DateTime()
  def tomorrow = now.plusDays(1)
  
  protected void setUp() {
    super.setUp()
  }

  protected void tearDown() {
    super.tearDown()
  }

  void testSomething() {
    def event = new Event(title: 'Repeating MWF Event')
    event.startTime = now.toDate()
    event.endTime = now.plusHours(1).toDate()
    event.location = "Regular location"
    event.recurType = EventRecurType.WEEKLY
    [MONDAY, WEDNESDAY, FRIDAY]*.toInteger().each { event.addToRecurDaysOfWeek(it) }
    event.addToExcludeDays(now.withDayOfWeek(MONDAY).plusWeeks(1).toDate())
    event.isRecurring = true
    event.save(flush: true)
  }
}
