// 帖子相关类型
export interface Post {
  id: number;
  title: string;
  content: string;
  author: string;
  createTime: string;
  summary: string;
  hasImage?: boolean;
  comments: Comment[];
}

export interface Comment {
  user: string;
  content: string;
  time: string;
}

// 活动相关类型
export interface Activity {
  id: number;
  title: string;
  type: string;
  startTime: string;
  endTime: string;
  participants: number;
  description: string;
  rules: string;
  questions?: Question[];
  question?: string;
  gameDescription?: string;
}

export interface Question {
  text: string;
  options: string[];
}

// API 响应类型
export interface ApiResponse<T> {
  code: number;
  message: string;
  data: T;
}
