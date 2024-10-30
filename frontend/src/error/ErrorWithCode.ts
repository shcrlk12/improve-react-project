export class ErrorWithCode extends Error {
  code: number;

  constructor(code: number, message: string) {
    super(message);
    this.code = code;
  }
}
