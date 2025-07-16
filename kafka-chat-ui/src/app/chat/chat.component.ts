import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-chat',
  standalone: true,
  imports: [CommonModule, FormsModule, HttpClientModule],
  templateUrl: './chat.component.html',
  styleUrls: ['./chat.component.css']
})
export class ChatComponent implements OnInit {
  message = '';
  messages: { content: string; mine?: boolean }[] = [];
  @ViewChild('chatBox') chatBox!: ElementRef;

  sessionId = Math.random().toString(36).substring(2);
  currentUser: string = ''; 

  constructor(private http: HttpClient) {}

  ngOnInit(): void {
    this.currentUser = this.getUserLabel(this.sessionId);
    this.fetchMessages();
    setInterval(() => this.fetchMessages(), 2000);
  }

  isSending = false;
  getUserLabel(sessionId: string): string {
    const lastChar = sessionId.charAt(sessionId.length - 1);
    const isEven = parseInt(lastChar, 36) % 2 === 0;
    return isEven ? 'User A' : 'User B';
  }
  sendMessage(): void {
    if (this.message.trim()) {
      this.isSending = true;
  
      const msg = {
        content: this.message,
        sessionId: this.sessionId 
      };
  
      this.http.post('http://localhost:8080/chat/send', msg, { responseType: 'text' })
        .subscribe(() => {
          this.messages.push({ content: this.message, mine: true });
          this.message = '';
          this.scrollToBottom();
          this.isSending = false;
        }, () => {
          this.isSending = false;
        });
    }
  }
  
  
  fetchMessages(): void {
    this.http.get<{ content: string; sessionId: string }[]>('http://localhost:8080/chat/messages')
      .subscribe(data => {
        this.messages = data.map(msg => ({
          content: msg.content,
          mine: msg.sessionId === this.sessionId
        }));
        this.scrollToBottom();
      });
  }

  scrollToBottom(): void {
    setTimeout(() => {
      if (this.chatBox) {
        this.chatBox.nativeElement.scrollTop = this.chatBox.nativeElement.scrollHeight;
      }
    }, 100);
  }
}
