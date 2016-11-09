package student;

import java.util.*;

class Member {
	
	private String mName;	// 학생 이름
	private String mID;		// 학생 번호

	// Member's (String, String) Constructor
	public Member(String argName, String argID) {
		mName = argName;
		mID   = argID;
	}
	
	// 입력한 학생번호를 mID에 대입
	public void setID(String argID) {
		mID = argID;
	}
	
	// 학생 번호를 리턴
	public String getID() {
		return mID;
	}
	
	// 입력한 학생이름을 mName에 대입
	public void setName(String argName) {
		mName = argName;
	}
	
	// 학생 이름을 리턴
	public String getName() {
		return mName;
	}
}

class Student extends Member {
	
	private int 			mTotalScore;		// 합계 변수
	private float 			mAverage;			// 평균 변수
	private int 			mRank;				// 등수 변수
	private int 			mNumOfSubject = 3;	// 과목의 개수를 나타내는 변수
	
	// Subject Class를 참조하여 과목의 개수만큼 생성한 배열
	private Subject[] 		mSubjectList = new Subject[getNumOfSubject()];
	
	// 입력한 학생 번호를 담기위한 1차원 배열, 중복 체크를 위해 생성
	private static int[] 	mIDCheck = new int[stdGradeMain.stdNum];
	
	// 학생 번호 중복 체크를 위해 독립적으로 선언된 객체들의 개수를 카운터하기 위한 변수
	private static int 		mIDCheckNum;
	private int 			mIDCheckOn;

	Scanner Scan = new Scanner(System.in);
	
	// Student's (String, String) Constructor
	public Student(String argName, String argID) {
		// 부모클래스인 Member에 default Constructor가 없기 때문에
		// 부모를 가리키는 super키워드를 이용하여
		// Member에 생성된 (String, String) 생성자를 호출
		super(argName, argID);
		
		// 학생 학번 중복 체크 알고리즘
		do {
			
			boolean tempB = false;
			
			do {
				System.out.println((stdGradeMain.stdNumber + 1) + "번째 학생의 학번을 입력하시오.");
				setID(Scan.next());	// 입력된 문자열을 mID에 대입
				tempB = isStringDouble(getID());

				if( tempB == false )
				{
					System.out.println("학번은 숫자만 입력이 가능합니다.");
				}
			} while ( tempB == false );
			
			// 문자열상태인 mID를 Int형으로 바꿔서 mIDCheck배열에 대입
			mIDCheck[mIDCheckNum] = Integer.parseInt(getID());
			
			// mIDCheck[0]번 부터 방금 배열에 대입된 값 전까지 비교하는 반복문
			for(int check = 0; check < mIDCheckNum; check++)
			{
				// 만약, 방금 배열에 대입된 값과 그 전에 입력된 값이 같다면
				// mID와 방금 배열에 대입된 공간을 초기화하고 반복문을 빠져나가시오.
				if(mIDCheck[check] == mIDCheck[mIDCheckNum])
				{
					System.out.println("동일한 학번을 가진 학생이 존재합니다.");
					System.out.println("학번을 다시 입력해주세요.");
					setID("");
					mIDCheck[mIDCheckNum] = 0;
					mIDCheckOn = 0;
					break;
				}
				// 방금 배열에 대입된 값과 그 전에 입력된 값이 없다면
				// mIDCheckOn을 +1 해주어라.
				else {
					++mIDCheckOn;
				}
			}
			
		} while ( mIDCheckOn !=  mIDCheckNum );	// mIDCheckOn과 mIDCheckNum이 같다면 do-while문을 빠져나와라.
		mIDCheckNum++;	// 현재까지 생성된 객체의 수
			
		System.out.println((stdGradeMain.stdNumber + 1) + "번째 학생의 이름을 입력하시오.");
		setName(Scan.next());	// 입력한 문자열을 mName에 대입
		
		// 과목 점수 입력
		for(int index = 0; index < 3; index++)
		{
			mSubjectList[index] = new Subject("", (short)0);
			
			switch( index ) {
				case 0:
					mSubjectList[index].setSubTitle("국어");
					System.out.println((stdGradeMain.stdNumber + 1) + "번째 학생의 " + mSubjectList[index].getSubTitle() + "성적을 입력하시오.");
					break;
					
				case 1:
					mSubjectList[index].setSubTitle("영어");
					System.out.println((stdGradeMain.stdNumber + 1) + "번째 학생의 " + mSubjectList[index].getSubTitle() + "성적을 입력하시오.");
					break;
					
				case 2:
					mSubjectList[index].setSubTitle("수학");
					System.out.println((stdGradeMain.stdNumber + 1) + "번째 학생의 " + mSubjectList[index].getSubTitle() + "성적을 입력하시오.");
					break;
				
				default:
					break;
			}
			
			mSubjectList[index].setScore(Scan.nextShort());
		}	// 과목 점수 입력 끝
		
		stdGradeMain.stdNumber++;	// 현재 입력된 학생의 순서를 나타낸다.
		calculateTotalScore();		// 합계 구하는 메소드
		calculateAverage();			// 평균 구하는 메소드
	}

	// 과목 수를 리턴
	public int getNumOfSubject() {
		return mNumOfSubject;
	}
	
	// 합계 구하는 메소드
	public void calculateTotalScore() {
		for(int index = 0; index < mSubjectList.length; index++)
		{
			// 현재 객체에 지정되어 있는 각 과목의 배열에 입력된 값을 리턴 받아서
			// 합계 변수에 더해준다.
			mTotalScore += mSubjectList[index].getScore();
		}
	}
	
	// 평균 구하는 메소드
	public void calculateAverage() {
		// 합계를 과목 수만큼 나눠서 평균 변수에 대입한다.
		mAverage = mTotalScore / (float)getNumOfSubject();
	}
	
	// 합계를 리턴
	public int getTotalScore() {
		return mTotalScore;
	}
	
	// 평균을 리턴
	public float getAverage() {
		return mAverage;
	}
	
	// 등수를 리턴
	public int getRank() {
		return mRank;
	}
	
	// 도출된 등수를 mRank에 대입
	public void setRank(int argRank) {
		mRank = argRank;
	}
	
	// 현재 객체에 저장되어 있는 학번, 이름, 과목점수, 합계, 평균, 등수를 출력
	public void prtStdGrade() {
		System.out.print(getID() + "\t" + getName() + "\t\t" 
				+ mSubjectList[0].getScore() + "\t" + mSubjectList[1].getScore() 
				+ "\t" + mSubjectList[2].getScore() + "\t" + mTotalScore + "\t");
		System.out.printf("%.1f\t", mAverage);
		System.out.println(mRank);
	}
	
	public boolean isStringDouble(String s) {
		try {
			Double.parseDouble(s);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
}

class Subject {
	
	private String mSubjectTitle = "NULL";	// 과목 이름 변수
	private short  mScore        = 0;		// 과목의 점수를 나타내는 변수
	
	// Subject's (String, short) Constructor
	public Subject(String argSubTitle, short argScore) {
		mSubjectTitle = argSubTitle;
		mScore        = argScore;
	}
	
	// 입력된 과목을 mSubjectTitle에 대입
	public void setSubTitle(String argSubTitle) {
		mSubjectTitle = argSubTitle;
	}
	
	// 과목 이름을 리턴
	public String getSubTitle() {
		return mSubjectTitle;
	}
	
	// 입력된 점수를 mScore에 대입
	public void setScore(short argScore) {
		mScore = argScore;
	}
	
	// 과목 점수를 리턴
	public short getScore() {
		return mScore;
	}
}

// 메인메소드가 선언되어 있는 클래스
public class stdGradeMain {
	
	static int stdNum;		// 독립적인 Student객체들의 수를 저장하기 위한 변수
	static int stdNumber;	// 
	
	public static void main(String[] args) {

		Scanner Scan = new Scanner(System.in);
		
		System.out.println("성적을 입력할 학생 수를 입력하시오");
		stdNum = Scan.nextInt();	// 입력된 학생 수를 클래스변수인 stdNum에 대입
		
		// Student 클래스를 참조하고 배열의 개수가 입력된 학생 수인 stdObj 1차원 배열을 생성
		Student[] stdObj = new Student[stdNum];
		
		// stdObj 각 배열의 객체를 생성하고, Student의 생성자가 동작
		for(int index = 0; index < stdNum; index++)
		{
			stdObj[index] = new Student("", "");
			
			// 모든 stdObj의 mRank값을 1로 초기화
			stdObj[index].setRank(1);
		}
		
		// 각 학생들의 합계를 비교하여 내림차순으로 정렬 시작
		for(int base = 0; base < stdObj.length - 1; base++)
		{	
			// 최대값 기준을 base로 지정하고,
			// 범위는 stdObj의 배열 끝에서 바로 앞에까지로 정함
			int       maxNum = base;
			
			// stdObj의 자료형이 Student 클래스라서
			// 각 객체의 정보를 바꿔줄 때 사용할 공간의 자료형을 Student로 지정
			Student   Temp;
			
			
			// 최대값인지 비교하는 배열 공간 지정 반복문
			// 기준이 base닌깐, 바로 다음 배열부터 비교시작
			for(int select = base + 1; select < stdObj.length; select++)
			{
				// 만약, 기준배열에 들어있는 값이 비교되고 있는 배열의 값보다 작을 경우
				// 비교되고 있는 배열의 자리수를 maxNum에 대입
				if(stdObj[base].getTotalScore() < stdObj[select].getTotalScore())
				{
					maxNum = select;
				}
			}
			
			// 바로 전에 만든 Temp 공간을 이용하여
			// 현재의 기준과 기준을 제외하고 제일 큰 수의 정보들을 교체
			Temp = stdObj[base];
			stdObj[base] = stdObj[maxNum];
			stdObj[maxNum] = Temp;
		}	// 내림차순 정렬 끝
		
		// 등수 지정 시작
		for(int base = 0; base < stdObj.length; base++)
		{
			// 반복문이 돌때마다 각 객체마다 들어갈 임시 공간을 1로 초기화
			int tempStdRank = 1;
			
			// 배열 수만큼 반복문으로 돌림
			for(int select = 0; select < stdObj.length; select++)
			{
				// 기준의 총점값보다 기준 이외의 총점값이 크다면 tempStdRank를 +1 해주어라.
				if(stdObj[base].getTotalScore() < stdObj[select].getTotalScore())
				{
					tempStdRank++;
					stdObj[base].setRank(tempStdRank);
				}
			}
		}
		
		System.out.println("학번" + "\t" + "이름" + "\t\t" + "국어" + "\t" 
				+ "영어" + "\t" + "수학" + "\t" + "합계" + "\t" + "평균" + "\t" + "등수");
		
		// 성적 출력 반복문
		for(int exit = 0; exit < stdObj.length; exit++)
		{
			stdObj[exit].prtStdGrade();
		}
	}
}
